
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s10
{
    public bra04_s10()
    {
    }
    //##**$$upso_select
    /*
    */
    public DOBJ CTLupso_select(DOBJ dobj)
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
            dobj  = CALLupso_select_OSP3(Conn, dobj);           //  채권금액 계산
            dobj  = CALLupso_select_SEL1(Conn, dobj);           //  업소채권정보
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
    public DOBJ CTLupso_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_select_OSP3(Conn, dobj);           //  채권금액 계산
        dobj  = CALLupso_select_SEL1(Conn, dobj);           //  업소채권정보
        return dobj;
    }
    // 채권금액 계산
    public DOBJ CALLupso_select_OSP3(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP3");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP3");
        String[]  incolumns ={"UPSO_CD","APPTN_YRMN"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
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
        String   spname ="GIBU.SP_GET_BOND_AMT_HIST";
        int[]    intypes={12, 12};;
        String[] outcolnms={"DEMD_MMCNT","START_YRMN","END_YRMN","TOT_USE_AMT","TOT_ADDT_AMT","TOT_EADDT_AMT","DSCT_AMT","TOT_DEMD_AMT"};;
        int[]    outtypes ={12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.setName("OSP3");
        robj.setRetcode(1);
        robj.Println("OSP3");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 업소채권정보
    public DOBJ CALLupso_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   TOT_ADDT_AMT = dobj.getRetObject("OSP3").getRecord().getDouble("TOT_ADDT_AMT");   //총 가산 금액
        double   TOT_USE_AMT = dobj.getRetObject("OSP3").getRecord().getDouble("TOT_USE_AMT");   //총 사용 금액
        double   TOT_DEMD_AMT = dobj.getRetObject("OSP3").getRecord().getDouble("TOT_DEMD_AMT");   //총 청구 금액
        double   DEMD_MMCNT = dobj.getRetObject("OSP3").getRecord().getDouble("DEMD_MMCNT");   //청구개월수
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("OSP3").getRecord().get("END_YRMN");   //종료년월
        double   TOT_EADDT_AMT = dobj.getRetObject("OSP3").getRecord().getDouble("TOT_EADDT_AMT");   //총 중가산 금액
        String   START_YRMN = dobj.getRetObject("OSP3").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.BRAN_CD  ,  A.UPSO_NM  ,  A.BIOWN_NUM  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  UPSO_ADDR  ,  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO  MNGEMSTR_ADDR  ,  A.MNGEMSTR_NM  ,  A.STAFF_CD  ,  A.UPSO_PHON  ,  A.MNGEMSTR_PHONNUM  ,  A.MNGEMSTR_RESINUM  ,  D.GRADNM  ,  NVL(:DEMD_MMCNT,0)  AS  DEMD_MMCNT    ,  :START_YRMN  ||  '01'  START_YRMN  ,  :END_YRMN  ||  '01'  END_YRMN  ,  NVL(:TOT_USE_AMT,0)  AS  TOT_USE_AMT    ,  NVL(:TOT_ADDT_AMT,0)  +  NVL(:TOT_EADDT_AMT,0)  AS  TOT_ADDT_AMT  ,  NVL(:TOT_DEMD_AMT,0)  AS  TOT_DEMD_AMT  ,  D.MONPRNCFEE  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZC.GRADNM,  ZB.MONPRNCFEE  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  D.UPSO_CD  =  A.UPSO_CD ";
        sobj.setSql(query);
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //총 가산 금액
        sobj.setDouble("TOT_USE_AMT", TOT_USE_AMT);               //총 사용 금액
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //총 청구 금액
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //청구개월수
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setDouble("TOT_EADDT_AMT", TOT_EADDT_AMT);               //총 중가산 금액
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$upso_select
    //##**$$bran_select
    /* * 프로그램명 : bra04_s10
    * 작성자 : 서정재
    * 작성일 : 2009/09/26
    * 설명    : 채권관리 : 지부에 해당 되는 모든 업소 정보중 해당월의 업소 정보를 가져옵니다.
    - Grid상에서 신규 업소를 등록시 해당 업소의 채권정보를 가져옵니다.
    - 가산금 : 가산금 + 중가산금
    - 미징수금액 : 미수금 - (가삼금 + 중가산금)으로 계산합니다.
    Input
    APPTN_YRMN (신청 일시)
    BRAN_CD (지부 코드)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbran_select(DOBJ dobj)
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
            dobj  = CALLbran_select_SEL1(Conn, dobj);           //  지부채권정보
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
    public DOBJ CTLbran_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbran_select_SEL1(Conn, dobj);           //  지부채권정보
        return dobj;
    }
    // 지부채권정보
    public DOBJ CALLbran_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S").getRecord().get("APPTN_YRMN");   //신청 일시
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.BRAN_CD  ,  D.DEPT_NM  BRAN_NM  ,  A.UPSO_NM  ,  A.BIOWN_NUM  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  UPSO_ADDR  ,  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO  MNGEMSTR_ADDR  ,  A.MNGEMSTR_NM  ,  A.STAFF_CD  ,  A.UPSO_PHON  ,  A.MNGEMSTR_HPNUM  AS  MNGEMSTR_PHONNUM  ,  A.MNGEMSTR_RESINUM  ,  B.MONPRNCFEE  ,  B.DEMD_MMCNT  ,  B.START_YRMN  ||  '01'  START_YRMN  ,  B.END_YRMN  ||  '01'  END_YRMN  ,  B.TOT_DEMD_AMT  ,  B.TOT_USE_AMT  ,  B.TOT_ADDT_AMT  ,  C.GRADNM  ,  DECODE(A.CLSBS_YRMN,  NULL,  '',  '폐업')  CLSBS  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  '',  '고소')  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  ACCU  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_DLGTN_CLAIM  B  ,  (   \n";
        query +=" SELECT  BC.GRADNM  ,  ZA.UPSO_CD  FROM  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  APPTN_YRMN  =  :APPTN_YRMN  )  XB  WHERE  XA.UPSO_CD  =  XB.UPSO_CD  )  ZA  GROUP  BY  ZA.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_BSTYPGRAD  BC  WHERE  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.BSTYP_CD  =  ZC.BSTYP_CD   \n";
        query +=" AND  ZB.UPSO_GRAD  =  ZC.GRAD_GBN   \n";
        query +=" AND  BC.BSTYP_CD  ='z'   \n";
        query +=" AND  BC.GRAD_GBN  =  ZC.BSTYP_CD  )  C  ,  INSA.TCPM_DEPT  D  WHERE  B.APPTN_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  A.BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD  ORDER  BY  BRAN_CD,  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$bran_select
    //##**$$claim_ins
    /* * 프로그램명 : bra04_s10
    * 작성자 : 박태현
    * 작성일 : 2009/10/07
    * 설명    : 채권업소 (채권추심 업체에서 채권을 받아냄) 내역을 저장/삭제한다.
    Input
    APPTN_YRMN (신청 일시)
    BIOWN_NUM (사업자 번호)
    BRAN_CD (지부 코드)
    DEMD_MMCNT (청구개월수)
    END_YRMN (종료년월)
    GRADNM (등급명)
    IUDFLAG (레코드상태구분)
    MNGEMSTR_ADDR (MNGEMSTR_ADDR)
    MNGEMSTR_NM (경영주 이름)
    MNGEMSTR_PHONNUM (경영주 전화번호)
    MNGEMSTR_RESINUM (경영주 주민번호)
    STAFF_CD (사원 코드)
    START_YRMN (시작년월)
    TOT_ADDT_AMT (총 가산 금액)
    TOT_DEMD_AMT (총 청구 금액)
    TOT_USE_AMT (총 사용 금액)
    UPSO_ADDR (업소 주소)
    UPSO_CD (업소 코드)
    UPSO_NM (업소 명)
    UPSO_PHON (업소 전화)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLclaim_ins(DOBJ dobj)
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
            dobj  = CALLclaim_ins_SEL1(Conn, dobj);           //  마감여부조회
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLclaim_ins_MPD10(Conn, dobj);           //  위임채권관리
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLclaim_ins( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLclaim_ins_SEL1(Conn, dobj);           //  마감여부조회
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLclaim_ins_MPD10(Conn, dobj);           //  위임채권관리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // 마감여부조회
    // 지부별 마감테이블의 해당 년월 정보가 있는지 체크한다
    public DOBJ CALLclaim_ins_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclaim_ins_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclaim_ins_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S1").getRecord().get("APPTN_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").firstRecord().get("UPSO_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(:END_YRMN,1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(:END_YRMN,5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 위임채권관리
    public DOBJ CALLclaim_ins_MPD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD10");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MPD10");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLclaim_ins_DEL9(Conn, dobj);           //  위임채권삭제
            }
            else
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLclaim_ins_SEL16(Conn, dobj);           //  등록여부확인
                if( dobj.getRetObject("SEL16").getRecord().getDouble("CNT") == 0)
                {
                    dobj  = CALLclaim_ins_INS7(Conn, dobj);           //  위임채권등록
                }
            }
        }
        return dobj;
    }
    // 등록여부확인
    public DOBJ CALLclaim_ins_SEL16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL16");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL16");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclaim_ins_SEL16(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL16");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclaim_ins_SEL16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("YRMN");   //신청 일시
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =:  UPSO_CD   \n";
        query +=" AND  APPTN_YRMN  =:APPTN_YRMN ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 위임채권삭제
    public DOBJ CALLclaim_ins_DEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclaim_ins_DEL9(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclaim_ins_DEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_DLGTN_CLAIM  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 위임채권등록
    public DOBJ CALLclaim_ins_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclaim_ins_INS7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclaim_ins_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //종료년월
        String INS_DATE ="";        //등록 일시
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //종료년월
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //월정료
        double   DEMD_MMCNT = dvobj.getRecord().getDouble("DEMD_MMCNT");   //청구개월수
        double   TOT_USE_AMT = dvobj.getRecord().getDouble("TOT_USE_AMT");   //총 사용 금액
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //총 청구 금액
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //총 가산 금액
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("APPTN_YRMN");   //신청 일시
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_DLGTN_CLAIM (INS_DATE, INSPRES_ID, STAFF_CD, APPTN_YRMN, TOT_ADDT_AMT, TOT_DEMD_AMT, TOT_USE_AMT, DEMD_MMCNT, MONPRNCFEE, UPSO_CD, END_YRMN, START_YRMN)  \n";
        query +=" values(sysdate, :INSPRES_ID , :STAFF_CD , :APPTN_YRMN , :TOT_ADDT_AMT , :TOT_DEMD_AMT , :TOT_USE_AMT , :DEMD_MMCNT , :MONPRNCFEE , :UPSO_CD , SUBSTR(:END_YRMN_1, 1, 6), SUBSTR(:START_YRMN_1, 1, 6))";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setString("END_YRMN_1", END_YRMN_1);               //종료년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //월정료
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //청구개월수
        sobj.setDouble("TOT_USE_AMT", TOT_USE_AMT);               //총 사용 금액
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //총 청구 금액
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //총 가산 금액
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$claim_ins
    //##**$$end
}
