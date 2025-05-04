
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s11
{
    public bra01_s11()
    {
    }
    //##**$$sel_each_bill
    /* 계산서 발행 시 기존에 청구내역(존재여부만 가림)과 월정료 만으로 발행하였으나, 무선로그할인 및 영업일수에 따른 할인을 추가하게 되면서, 청구내역과 상관없이 계산을 하도록 변경해야 하는 상황이 발생. (청구내역으로 하면 월정료가 아닌 사용료 USE_AMT 로 해야하나, 통합징수때문에 금액이 크게 잡힘)
    따라서, 청구내역 관계없이 지로와 자동이체 두가지로 나눠서 월정료 및 통합징수 금액을 계산하도록 변경
    +++++ 가산금 중가산금을 추가해야해서 금액에 NVL로 청구내역의 가산금+중가산금을 더하도록 추가
    2020-06-09 장태진
    */
    public DOBJ CTLsel_each_bill(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLsel_each_bill_SEL1(Conn, dobj);           //  업소중복체크및거래처가져오기
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLsel_each_bill( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_each_bill_SEL1(Conn, dobj);           //  업소중복체크및거래처가져오기
        return dobj;
    }
    // 업소중복체크및거래처가져오기
    public DOBJ CALLsel_each_bill_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_each_bill_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_each_bill_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S").getRecord().get("APPTN_YRMN");   //신청 일시
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XC.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MST  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  :APPTN_YRMN  )  AS  DUPCNT  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  :APPTN_YRMN)  >  0)  THEN  XB.MONPRNCFEE2  -  TRUNC(XB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XB.BSTYP_CD))  ELSE  XB.MONPRNCFEE  END)  +  NVL((SELECT  NVL(ADDT_AMT  +  EADDT_AMT,  0)  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  START_YRMN  =   \n";
        query +=" (SELECT  MIN(START_YRMN)  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :APPTN_YRMN)),  0)  AS  MONPRNCFEE  ,  GIBU.FT_SPLIT(GIBU.FT_GET_DEMD_MONPRNCFEE(XA.UPSO_CD,  :APPTN_YRMN),  ',',  0)  AS  MONPRNCFEE2  ,  SUBSTR(:APPTN_YRMN,0,4)  ||  '년  '  ||  SUBSTR(:APPTN_YRMN,5,2)  ||  '월  음악사용료'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%무선로그%'  THEN  MONPRNCFEE  -  TRUNC(MONPRNCFEE  *  0.7,  -1)  ELSE  MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%무선로그%'  THEN  MONPRNCFEE2  -  TRUNC(MONPRNCFEE2  *  0.7,  -1)  ELSE  MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <=  20  THEN  TRUNC(ZB.MONPRNCFEE  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <=  20  THEN  TRUNC(ZB.MONPRNCFEE2  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  ZB.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  )  XB  ,  FIDU.TLEV_BSCON  XC  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AS  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN)  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD)  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XC.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MST  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  :APPTN_YRMN  )  AS  DUPCNT  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  :APPTN_YRMN)  >  0)  THEN  TRUNC(XB.MONPRNCFEE2  -  TRUNC(XB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XB.BSTYP_CD))  -  TRUNC((XB.MONPRNCFEE2  -  TRUNC(XB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XB.BSTYP_CD)))  *  0.01,  -1))  ELSE  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y')  >  0  THEN  TRUNC(XB.MONPRNCFEE  -  TRUNC(XB.MONPRNCFEE  *  0.01,  -1))  ELSE  TRUNC(XB.MONPRNCFEE  -  TRUNC(XB.MONPRNCFEE  *  0.01,  -1),  -1)  END)  END)  +  NVL((SELECT  NVL(ADDT_AMT  +  EADDT_AMT,  0)  FROM  GIBU.TBRA_DEMD_AUTO_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  START_YRMN  =   \n";
        query +=" (SELECT  MIN(START_YRMN)  FROM  GIBU.TBRA_DEMD_AUTO_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :APPTN_YRMN)),  0)  AS  MONPRNCFEE  ,  GIBU.FT_SPLIT(GIBU.FT_GET_DEMD_MONPRNCFEE(XA.UPSO_CD,  :APPTN_YRMN),  ',',  0)  AS  MONPRNCFEE2  ,  SUBSTR(:APPTN_YRMN,0,4)  ||  '년  '  ||  SUBSTR(:APPTN_YRMN,5,2)  ||  '월  음악사용료'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%무선로그%'  THEN  MONPRNCFEE  -  TRUNC(MONPRNCFEE  *  0.7,  -1)  ELSE  MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%무선로그%'  THEN  MONPRNCFEE2  -  TRUNC(MONPRNCFEE2  *  0.7,  -1)  ELSE  MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <=  20  THEN  TRUNC(ZB.MONPRNCFEE  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <=  20  THEN  TRUNC(ZB.MONPRNCFEE2  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  ZB.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  )  XB  ,  FIDU.TLEV_BSCON  XC  ,  GIBU.TBRA_UPSO_AUTO  XE  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XE.TERM_YN  =  'N'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.UPSO_CD  IN   \n";
        query +=" (SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AS  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN)  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD)   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$sel_each_bill
    //##**$$mng_bill_iss
    /*
    */
    public DOBJ CTLmng_bill_iss(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLmng_bill_iss_MIUD3(Conn, dobj);           //  분기
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLmng_bill_iss( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_bill_iss_MIUD3(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLmng_bill_iss_MIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_bill_iss_SEL1(Conn, dobj);           //  DEMD_NUM 발번
                dobj  = CALLmng_bill_iss_INS2(Conn, dobj);           //  계산서 마스터 등록
                dobj  = CALLmng_bill_iss_XIUD11(Conn, dobj);           //  계산서 디테일 등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_bill_iss_UPD12(Conn, dobj);           //  계산서 디테일 수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_bill_iss_DEL8(Conn, dobj);           //  계산서 디테일 삭제
                dobj  = CALLmng_bill_iss_SEL11(Conn, dobj);           //  잔여 상세 계산서 여부확인
                if( dobj.getRetObject("SEL11").getRecord().getDouble("CNT") == 0)
                {
                    dobj  = CALLmng_bill_iss_DEL7(Conn, dobj);           //  계산서 마스터 삭제
                }
            }
        }
        return dobj;
    }
    // 계산서 디테일 삭제
    public DOBJ CALLmng_bill_iss_DEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bill_iss_DEL8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_DEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_ISS_DTL  \n";
        query +=" where APPRV_NUM=:APPRV_NUM";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        return sobj;
    }
    // 계산서 디테일 수정
    public DOBJ CALLmng_bill_iss_UPD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bill_iss_UPD12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_UPD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   ISS_DAY = dvobj.getRecord().get("ISS_DAY");   //발행 일자
        String   ISS_BRE = dvobj.getRecord().get("ISS_BRE");   //발행 내역
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        double   ISS_AMT = dobj.getRetObject("R").getRecord().getDouble("KOMCA_AMT");   //발행 금액
        String   ISS_COMPL_YN = dobj.getRetObject("R").getRecord().get("ISSADD_YN");   //발행 완료 여부
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BILL_ISS_DTL  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ISS_COMPL_YN=:ISS_COMPL_YN , ISS_AMT=:ISS_AMT , BILL_GBN=:BILL_GBN , ISS_BRE=:ISS_BRE , ISS_DAY=:ISS_DAY , MOD_DATE=SYSDATE , REMAK=:REMAK  \n";
        query +=" where DEMD_NUM=:DEMD_NUM  \n";
        query +=" and APPRV_NUM=:APPRV_NUM";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("ISS_BRE", ISS_BRE);               //발행 내역
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setDouble("ISS_AMT", ISS_AMT);               //발행 금액
        sobj.setString("ISS_COMPL_YN", ISS_COMPL_YN);               //발행 완료 여부
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // DEMD_NUM 발번
    public DOBJ CALLmng_bill_iss_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_bill_iss_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("APPTN_YRMN");   //신청 일시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :APPTN_YRMN  ||  LPAD(NVL(MAX(TO_NUMBER(SUBSTR(DEMD_NUM,  7)))  +  1,  0),  4,  '0')  AS  NEW_DEMD_NUM  FROM  GIBU.TBRA_BILL_ISS_MST  WHERE  APPTN_YRMN  =  :APPTN_YRMN ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        return sobj;
    }
    // 잔여 상세 계산서 여부확인
    public DOBJ CALLmng_bill_iss_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_bill_iss_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("R").getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_BILL_ISS_DTL  WHERE  DEMD_NUM  =  :DEMD_NUM ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    // 계산서 마스터 등록
    public DOBJ CALLmng_bill_iss_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bill_iss_INS2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("APPTN_YRMN");   //신청 일시
        String   BRAN_CD = dobj.getRetObject("S1").getRecord().get("BRAN_CD");   //지부 코드
        String   DEMD_NUM = dobj.getRetObject("SEL1").getRecord().get("NEW_DEMD_NUM");   //청구서번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BILL_ISS_MST (DEMD_NUM, INS_DATE, INSPRES_ID, APPTN_YRMN, UPSO_CD, APPTN_GBN, BRAN_CD, BSCON_CD)  \n";
        query +=" values(:DEMD_NUM , SYSDATE, :INSPRES_ID , :APPTN_YRMN , :UPSO_CD , :APPTN_GBN , :BRAN_CD , :BSCON_CD )";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 계산서 디테일 등록
    public DOBJ CALLmng_bill_iss_XIUD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD11");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bill_iss_XIUD11(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_XIUD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("APPTN_YRMN");   //신청 일시
        String   BILL_GBN = dobj.getRetObject("R").getRecord().get("BILL_GBN");   //계산서 구분
        String   DEMD_NUM = dobj.getRetObject("SEL1").getRecord().get("NEW_DEMD_NUM");   //청구서번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   ISS_BRE = dobj.getRetObject("R").getRecord().get("ISS_BRE");   //발행 내역
        String   ISS_COMPL_YN = dobj.getRetObject("R").getRecord().get("ISSADD_YN");   //발행 완료 여부
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //발행 일자
        double   KOMCA_AMT = dobj.getRetObject("R").getRecord().getDouble("KOMCA_AMT");   //KOMCA_AMT
        double   MONPRNCFEE2 = dobj.getRetObject("R").getRecord().getDouble("MONPRNCFEE2");   //기준월정료
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_BILL_ISS_DTL (DEMD_NUM, APPRV_NUM, BILL_KND, BILL_GBN, SUPPBSCON_CD, ISS_BRE, ISS_AMT, ISS_DAY, REMAK, ISS_COMPL_YN, INSPRES_ID, INS_DATE) SELECT DEMD_NUM , ( SELECT TO_CHAR(SYSDATE, 'YYYYMMDD') || TRIM(TO_CHAR(NVL(MAX(TO_NUMBER(SUBSTR(APPRV_NUM, 9), 'XXX')), 0) + RN, '0XX')) AS APPRV_NUM FROM GIBU.TBRA_BILL_ISS_DTL WHERE APPRV_NUM LIKE TO_CHAR(SYSDATE, 'YYYYMMDD') || '%' AND TO_NUMBER(SUBSTR(APPRV_NUM, 9), 'XXX') > 0 ) AS APPRV_NUM , BILL_KND , BILL_GBN , SUPPBSCON_CD , ISS_BRE , ISS_AMT , ISS_DAY , REMAK , ISS_COMPL_YN , INSPRES_ID , SYSDATE FROM ( SELECT :DEMD_NUM AS DEMD_NUM , ROWNUM AS RN , BILL_KND , :BILL_GBN AS BILL_GBN , SUPPBSCON_CD , :ISS_BRE AS ISS_BRE , TO_NUMBER(ISS_AMT) AS ISS_AMT , :ISS_DAY AS ISS_DAY , :REMAK AS REMAK , :ISS_COMPL_YN AS ISS_COMPL_YN , :INSPRES_ID AS INSPRES_ID FROM ( SELECT 'KOMCA' AS SUPPBSCON_CD , TO_NUMBER(:KOMCA_AMT) AS ISS_AMT , 1 AS R_NUM , '4' AS BILL_KND FROM DUAL UNION ALL SELECT 'T0000001' AS SUPPBSCON_CD , FLOOR(:MONPRNCFEE2 * (SELECT MNG_RATE / 100 FROM GIBU.TBRA_BSCON_MNG_RATE WHERE BSTYP_CD = GIBU.FT_GET_BSTYP_INFO(:UPSO_CD) AND APPL_DAY < :ISS_DAY)) AS ISS_AMT , 2 AS R_NUM , '5' AS BILL_KND FROM DUAL WHERE GIBU.FT_GET_BSTYP_INFO(:UPSO_CD) IN ('k', 'l', 'o') UNION ALL SELECT BSCON_CD , (CASE WHEN BSCON_CD = 'T0000001' THEN FLOOR(:MONPRNCFEE2 * (SELECT MNG_RATE / 100 FROM GIBU.TBRA_BSCON_MNG_RATE WHERE BSTYP_CD = GIBU.FT_GET_BSTYP_INFO(:UPSO_CD) AND APPL_DAY < :ISS_DAY)) ELSE DEMD_AMT END) AS ISS_AMT , ROWNUM + 1 AS R_NUM , (CASE WHEN (SELECT ATAX_YN FROM GIBU.TBRA_BSCON_CONTRINFO WHERE UPSO_CD = A.UPSO_CD AND BSCON_CD = A.BSCON_CD AND BSCON_UPSO_CD = A.BSCON_UPSO_CD AND SEQ = (SELECT MAX(SEQ) FROM GIBU.TBRA_BSCON_CONTRINFO WHERE UPSO_CD = A.UPSO_CD AND BSCON_CD = A.BSCON_CD AND BSCON_UPSO_CD = A.BSCON_UPSO_CD)) = 'Y' THEN '6' ELSE '5' END) AS BILL_KND FROM GIBU.TBRA_BSCON_DEMD_UPLOAD A WHERE DEMD_YRMN = :APPTN_YRMN AND UPSO_CD = :UPSO_CD ) A )";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("ISS_BRE", ISS_BRE);               //발행 내역
        sobj.setString("ISS_COMPL_YN", ISS_COMPL_YN);               //발행 완료 여부
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setDouble("KOMCA_AMT", KOMCA_AMT);               //KOMCA_AMT
        sobj.setDouble("MONPRNCFEE2", MONPRNCFEE2);               //기준월정료
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 계산서 마스터 삭제
    public DOBJ CALLmng_bill_iss_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bill_iss_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_ISS_MST  \n";
        query +=" where DEMD_NUM=:DEMD_NUM";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    //##**$$mng_bill_iss
    //##**$$chk_group_bill
    /*
    */
    public DOBJ CTLchk_group_bill(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLchk_group_bill_SEL1(Conn, dobj);           //  체크
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLchk_group_bill( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchk_group_bill_SEL1(Conn, dobj);           //  체크
        return dobj;
    }
    // 체크
    public DOBJ CALLchk_group_bill_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLchk_group_bill_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_group_bill_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S").getRecord().get("APPTN_YRMN");   //신청 일시
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  DUPCNT,  NVL(MAX((SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MST  AA  ,  GIBU.TBRA_BILL_ISS_DTL  BB  WHERE  AA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  AA.DEMD_NUM  =  BB.DEMD_NUM   \n";
        query +=" AND  BB.ISS_COMPL_YN  =  '2'   \n";
        query +=" AND  AA.APPTN_GBN  ='1'  )),0)  AS  ISS_COMPL_YN_CNT  FROM  GIBU.TBRA_BILL_ISS_MST  A  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  APPTN_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  APPTN_GBN  ='1' ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$chk_group_bill
    //##**$$save_group_bill
    /*
    */
    public DOBJ CTLsave_group_bill(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLsave_group_bill_OSP13(Conn, dobj);           //  단체계산서발급 프로시저
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLsave_group_bill( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_group_bill_OSP13(Conn, dobj);           //  단체계산서발급 프로시저
        return dobj;
    }
    // 단체계산서발급 프로시저
    public DOBJ CALLsave_group_bill_OSP13(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP13");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        String[]  incolumns ={"BRAN_CD","APPTN_YRMN","ISS_DAY","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //등록자 ID
            record.put("INSPRES_ID",INSPRES_ID);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_DEMD_BILL";
        int[]    intypes={12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP13");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$save_group_bill
    //##**$$sel_bill_iss
    /*
    */
    public DOBJ CTLsel_bill_iss(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLsel_bill_iss_SEL1(Conn, dobj);           //  계산서 조회
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLsel_bill_iss( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bill_iss_SEL1(Conn, dobj);           //  계산서 조회
        return dobj;
    }
    // 계산서 조회
    public DOBJ CALLsel_bill_iss_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bill_iss_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bill_iss_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S").getRecord().get("APPTN_YRMN");   //신청 일시
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_NUM  ,  A.UPSO_CD  ,  B.APPRV_NUM  ,  C.UPSO_NM  ,  A.BSCON_CD  ,  D.BSCONHAN_NM  ,  A.BRAN_CD  ,  E.DEPT_NM  AS  BRAN_NM  ,  A.APPTN_YRMN  ,  B.ISS_BRE  ,  B.ISS_AMT  AS  KOMCA_AMT  ,   \n";
        query +=" (SELECT  SUM(ISS_AMT)  FROM  GIBU.TBRA_BILL_ISS_DTL  WHERE  DEMD_NUM  =  A.DEMD_NUM)  AS  ISS_AMT  ,  B.BILL_GBN  ,  B.REMAK  ,  B.ISS_DAY  ,  DECODE((SELECT  COUNT(1)  FROM  FIDU.BILL_TRANS  WHERE  BILLSEQ  =  B.BILL_NUM),  0,  1,  1,  2)  AS  ISS_COMPL_YN  ,  NVL(B.ISS_COMPL_YN,  0)  AS  ISSADD_YN  ,  B.BILL_NUM  ,  DECODE(A.APPTN_GBN,  '1',  '단체',  '2',  '개별')  APPTN_GBN  ,  B.BILL_KND  ,  B.SUPPBSCON_CD  ,  (CASE  WHEN  B.SUPPBSCON_CD  =  'KOMCA'  THEN  '(사)한국음악저작권협회'  ELSE   \n";
        query +=" (SELECT  HANMB_NM  FROM  FIDU.TMEM_MB  WHERE  MB_CD  =  B.SUPPBSCON_CD)  END)  AS  SUPPBSCON_NM  ,  FIDU.GET_STAFF_NM(C.STAFF_CD)  AS  STAFF_NM  FROM  GIBU.TBRA_BILL_ISS_MST  A  ,  GIBU.TBRA_BILL_ISS_DTL  B  ,  GIBU.TBRA_UPSO  C  ,  FIDU.TLEV_BSCON  D  ,  INSA.TCPM_DEPT  E  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  A.BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.DEMD_NUM  =  B.DEMD_NUM   \n";
        query +=" AND  B.BILL_KND  IN  ('4',  '5',  '6')   \n";
        query +=" AND  A.APPTN_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  C.BRAN_CD  =  A.BRAN_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  D.BSCON_CD  =  A.BSCON_CD   \n";
        query +=" AND  E.GIBU  =  A.BRAN_CD  ORDER  BY  C.BRAN_CD,  A.INS_DATE,  A.BSCON_CD,  B.SUPPBSCON_CD ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$sel_bill_iss
    //##**$$bra01_s11_groupIssue
    /* * 프로그램명 : bra01_s11
    * 작성자 : 999999
    * 작성일 : 2009/10/09
    * 설명 :
    * 수정일1: 2010/02/10
    * 수정자 :  서정재
    * 수정내용 : 자동이체 업소는 월정료의 1% 할인된 금액을 보여준다.
    * 수정일2: 2010/02/18
    * 수정자 :  서정재
    * 수정내용 : 계산서 발행금액은 해당월에 청구된 금액으로 표시해달라.
    */
    public DOBJ CTLbra01_s11_groupIssue(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLbra01_s11_groupIssue_DEL2(Conn, dobj);           //  기존데이터삭제
            dobj  = CALLbra01_s11_groupIssue_SEL1(Conn, dobj);           //  대량신청조회
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLbra01_s11_groupIssue( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_groupIssue_DEL2(Conn, dobj);           //  기존데이터삭제
        dobj  = CALLbra01_s11_groupIssue_SEL1(Conn, dobj);           //  대량신청조회
        return dobj;
    }
    // 기존데이터삭제
    public DOBJ CALLbra01_s11_groupIssue_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra01_s11_groupIssue_DEL2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_groupIssue_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPTN_YRMN ="";        //신청 일시
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   APPTN_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //신청 일시
        String   APPTN_GBN ="1";   //신청 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" where APPTN_YRMN=SUBSTR(:APPTN_YRMN_1,0,6)  \n";
        query +=" and APPTN_GBN=:APPTN_GBN  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //신청 일시
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        return sobj;
    }
    // 대량신청조회
    public DOBJ CALLbra01_s11_groupIssue_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra01_s11_groupIssue_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_groupIssue_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ISSUE_YRMN = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");        //발행 년월
        String   ISSUE_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //발행 년월
        String   ISS_DAY = dobj.getRetObject("S").getRecord().get("ISS_DAY");   //발행 일자
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,0,6))  =  0)   \n";
        query +=" AND  ((SELECT  COUNT(1)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  XA.UPSO_CD)  >  0)  THEN  XD.MONPRNCFEE  WHEN  GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,0,6))  >  0  THEN  XD.MONPRNCFEE  -  TRUNC(XD.MONPRNCFEE  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XD.BSTYP_CD))  ELSE  XD.TOT_DEMD_AMT  END)  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  :ISS_DAY  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_OCR  XD  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XD.DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,0,6)   \n";
        query +=" AND  XD.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.DEMD_GBN  =  '32'   \n";
        query +=" AND  XD.TOT_DEMD_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  ,  XF.MONPRNCFEE  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  :ISS_DAY  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZB.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,0,6))  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XF  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XF.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,0,6)   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  DEMD_GBN  =  '32'   \n";
        query +=" AND  TOT_DEMD_AMT  >  0  )   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN  (   \n";
        query +=" SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN  )  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD  )  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  ,  XE.TOT_DEMD_AMT  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  :ISS_DAY  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_AUTO  XE  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,0,6)   \n";
        query +=" AND  XE.DEMD_GBN  =  '31'   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.TOT_DEMD_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  ,  TRUNC(XF.MONPRNCFEE  -  TRUNC(XF.MONPRNCFEE  *  0.01,  -1),  -1)  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  :ISS_DAY  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_UPSO_AUTO  XE  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZB.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,0,6))  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XF  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.TERM_YN  =  'N'   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,0,6)   \n";
        query +=" AND  DEMD_GBN  =  '31'   \n";
        query +=" AND  TOT_DEMD_AMT  >  0  )   \n";
        query +=" AND  XE.UPSO_CD  IN  (   \n";
        query +=" SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN  )  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD  )   \n";
        query +=" AND  XF.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD  ORDER  BY  BSCON_CD,  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("ISSUE_YRMN_1", ISSUE_YRMN_1);               //발행 년월
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$bra01_s11_groupIssue
    //##**$$bra01_s11_dupCheck
    /* * 프로그램명 : bra01_s11
    * 작성자 : 999999
    * 작성일 : 2009/10/08
    * 설명 :
    * 수정일1: 2010/02/10
    * 수정자 :
    * 수정내용 : 자동이체 업소인 경우 월정료에서 1% 할인된 금액을 보여준다.
    EX1) 자동이체 업소  :3009414A(월정료: 300,000 -> 표시금액: 270,000)
    EX2) 일반업소  : 3010399A (월정료: 70,000 -> 표시금액: 70,000)
    * 수정일2: 2010/04/22
    * 수정자 :
    * 수정내용 : 개별발급시에는 업소정보에서 계산서발행여부체크 표시와 상관없이 거래처 정보가 등록되어 있으면 된다.
    물론 해당 월에 청구정보가 있어야 함.
    * 수정일2: 2010/04/27
    * 수정자 :
    * 수정내용 : 신규개발업소도 계산서발급이 가능하다. 즉 청구정보가 없을 경우에는 해당업소의 월정료를  보여준다.
    */
    public DOBJ CTLbra01_s11_dupCheck(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLbra01_s11_dupCheck_SEL11(Conn, dobj);           //  업소중복체크및거래처가져오기
            if( dobj.getRetObject("SEL11").getRecordCnt() == 0)
            {
                dobj  = CALLbra01_s11_dupCheck_SEL3(Conn, dobj);           //  신규개발업소월정료정보
                dobj  = CALLbra01_s11_dupCheck_SEL1( dobj);        //  최종결과
            }
            else
            {
                dobj  = CALLbra01_s11_dupCheck_SEL1( dobj);        //  최종결과
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLbra01_s11_dupCheck( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_dupCheck_SEL11(Conn, dobj);           //  업소중복체크및거래처가져오기
        if( dobj.getRetObject("SEL11").getRecordCnt() == 0)
        {
            dobj  = CALLbra01_s11_dupCheck_SEL3(Conn, dobj);           //  신규개발업소월정료정보
            dobj  = CALLbra01_s11_dupCheck_SEL1( dobj);        //  최종결과
        }
        else
        {
            dobj  = CALLbra01_s11_dupCheck_SEL1( dobj);        //  최종결과
        }
        return dobj;
    }
    // 업소중복체크및거래처가져오기
    public DOBJ CALLbra01_s11_dupCheck_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra01_s11_dupCheck_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_dupCheck_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ISSUE_YRMN ="";        //발행 년월
        String   ISSUE_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //발행 년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(:ISSUE_YRMN_1,1,6)  )  AS  DUPCNT  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,1,6))  =  0)   \n";
        query +=" AND  ((SELECT  COUNT(1)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  XA.UPSO_CD)  >  0)  THEN  XD.MONPRNCFEE  WHEN  GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,1,6))  >  0  THEN  XD.MONPRNCFEE  -  TRUNC(XD.MONPRNCFEE  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XD.BSTYP_CD))  ELSE  XD.TOT_DEMD_AMT  END)  AS  MONPRNCFEE  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_OCR  XD  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XD.DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,1,6)   \n";
        query +=" AND  XD.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.DEMD_GBN  =  '32'   \n";
        query +=" AND  XD.TOT_DEMD_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(:ISSUE_YRMN_1,1,6)  )  AS  DUPCNT  ,  XE.TOT_DEMD_AMT  AS  MONPRNCFEE  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_AUTO  XE  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,1,6)   \n";
        query +=" AND  XE.DEMD_GBN  =  '31'   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.TOT_DEMD_AMT  >  0  ORDER  BY  BSCON_CD,  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("ISSUE_YRMN_1", ISSUE_YRMN_1);               //발행 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 신규개발업소월정료정보
    // 청구서가 발행 후 등록된 신규개발업소인경우 월정료를 보여준다. 2010.04.26 김현이계장님
    public DOBJ CALLbra01_s11_dupCheck_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra01_s11_dupCheck_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_dupCheck_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ISSUE_YRMN ="";        //발행 년월
        String   ISSUE_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //발행 년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XC.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(:ISSUE_YRMN_1,1,6)  )  AS  DUPCNT  ,  XB.MONPRNCFEE  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZB.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,1,6))  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  FIDU.TLEV_BSCON  XC  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSCON_CD  =  XA.BSCON_CD ";
        sobj.setSql(query);
        sobj.setString("ISSUE_YRMN_1", ISSUE_YRMN_1);               //발행 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 최종결과
    public DOBJ CALLbra01_s11_dupCheck_SEL1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL11, SEL3","");
        rvobj.setName("SEL1") ;
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    //##**$$bra01_s11_dupCheck
    //##**$$bra01_s11_groupDupCheck
    /* * 프로그램명 : bra01_s11
    * 작성자 : 999999
    * 작성일 : 2009/10/08
    * 설명 : 1.단체발급시 해당년월에 이미 단체발급(APPTN_GBN='1') 으로 계산서발행이 완료되었을 경우에는 삭제할수 없는 경고를 보낸다.
    - ISS_COMPL_YN_CNT 로 체크
    2.단체발급시 해당년월에 이미 단체발급(APPTN_GBN='1') 으로 계산서신청이 되어있을 경우 삭제하고 다시 발급하냐는 안내문을 보여준다.
    - DUPCNT 로 체크
    * 수정일1: 권남균
    * 수정자 : 2010/06/11
    * 수정내용 : ISS_COMPL_YN_CNT 체크시 ISS_COMPL_YN = '1' => ISS_COMPL_YN = '2'로 변경
    */
    public DOBJ CTLbra01_s11_groupDupCheck(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLbra01_s11_groupDupCheck_SEL1(Conn, dobj);           //  단체발급중복체크
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLbra01_s11_groupDupCheck( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_groupDupCheck_SEL1(Conn, dobj);           //  단체발급중복체크
        return dobj;
    }
    // 단체발급중복체크
    public DOBJ CALLbra01_s11_groupDupCheck_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra01_s11_groupDupCheck_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_groupDupCheck_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPTN_YRMN ="";        //신청 일시
        String   APPTN_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //신청 일시
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  DUPCNT,  NVL(MAX((SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(:APPTN_YRMN_1,1,6)   \n";
        query +=" AND  ISS_COMPL_YN  =  '2'   \n";
        query +=" AND  APPTN_GBN  ='1'  )),0)  AS  ISS_COMPL_YN_CNT  FROM  GIBU.TBRA_BILL_ISS_MNG  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  APPTN_YRMN  =  SUBSTR(:APPTN_YRMN_1,1,6)   \n";
        query +=" AND  APPTN_GBN  ='1' ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //신청 일시
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$bra01_s11_groupDupCheck
    //##**$$bra01_s11_delete_chk
    /* 임시- 사용할지 말지 결정안했음 2010.04.26
    */
    public DOBJ CTLbra01_s11_delete_chk(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLbra01_s11_delete_chk_SEL1(Conn, dobj);           //  삭제가능여부체크
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLbra01_s11_delete_chk( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_delete_chk_SEL1(Conn, dobj);           //  삭제가능여부체크
        return dobj;
    }
    // 삭제가능여부체크
    // 신탁회계 계산서 발행 테이블에서 해당 계산서번호가 삭제되었는지 체크
    public DOBJ CALLbra01_s11_delete_chk_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra01_s11_delete_chk_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_delete_chk_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  FIDU.TTAC_BILL  WHERE  BILL_NUM  =  :BILL_NUM ";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    //##**$$bra01_s11_delete_chk
    //##**$$bra01_s11_dupCheck02
    /* * 프로그램명 : bra01_s11
    * 작성자 : 999999
    * 작성일 : 2009/10/08
    * 설명 :
    * 수정일1: 2010.02.10
    * 수정자 : 서정재
    * 수정내용 : 자동이체 업소인 경우 월정료에서 1% 할인된 금액을 보여준다.
    EX1) 자동이체 업소 거래처 코드 : J1227 (월정료: 300,000 -> 표시금액: 270,000)
    EX2) 일반업소 거래처 코드 : J1292 (월정료: 70,000 -> 표시금액: 70,000)
    사용안하는 METHOD인듯;;;;
    */
    public DOBJ CTLbra01_s11_dupCheck02(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLbra01_s11_dupCheck02_SEL1(Conn, dobj);           //  중복체크및거래처업소체크
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLbra01_s11_dupCheck02( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_dupCheck02_SEL1(Conn, dobj);           //  중복체크및거래처업소체크
        return dobj;
    }
    // 중복체크및거래처업소체크
    public DOBJ CALLbra01_s11_dupCheck02_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra01_s11_dupCheck02_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_dupCheck02_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ISSUE_YRMN ="";        //발행 년월
        String   ISSUE_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //발행 년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.MONPRNCFEE  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  APPTN_YRMN  =  SUBSTR(:ISSUE_YRMN_1,0,6)  )  DUPCNT  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),5,2)  ||  '월  음악사용료'  AS  CTENT  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  DECODE(ZD.UPSO_CD,  NULL,  ZB.MONPRNCFEE,  (ZB.MONPRNCFEE*0.99))  MONPRNCFEE  ,  ZC.GRADNM  ,  ZB.BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ,  A.UPSO_CD  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.APPL_DAY  <=  TO_CHAR(SYSDATE,  'YYYYMMDD')  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_UPSO_AUTO  ZD  WHERE  ZA.UPSO_CD  =  ZB.UPSO_CD   \n";
        query +=" AND  ZA.JOIN_SEQ  =  ZB.JOIN_SEQ   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  'z'   \n";
        query +=" AND  ZD.TERM_YN(+)  ='N'   \n";
        query +=" AND  ZD.UPSO_CD(+)  =  ZB.UPSO_CD  )  XB  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("ISSUE_YRMN_1", ISSUE_YRMN_1);               //발행 년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    //##**$$bra01_s11_dupCheck02
    //##**$$end
}
