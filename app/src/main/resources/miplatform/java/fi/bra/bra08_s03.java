
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra08_s03
{
    public bra08_s03()
    {
    }
    //##**$$use_appr_req_list
    /* * 프로그램명 : bra08_s03
    * 작성자 : 서정재
    * 작성일 : 2009/11/05
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLuse_appr_req_list(DOBJ dobj)
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
            dobj  = CALLuse_appr_req_list_SEL1(Conn, dobj);           //  사용승인이행요청리스트
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
    public DOBJ CTLuse_appr_req_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLuse_appr_req_list_SEL1(Conn, dobj);           //  사용승인이행요청리스트
        return dobj;
    }
    // 사용승인이행요청리스트
    public DOBJ CALLuse_appr_req_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLuse_appr_req_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLuse_appr_req_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  B.UPSO_NM  ,  DECODE(B.PAYPRES_GBN,  'B',  B.MNGEMSTR_NM,  B.PERMMSTR_NM)  AS  MNGEMSTR_NM  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ZIP,  'B',  B.MNGEMSTR_NEW_ZIP,  B.PERMMSTR_NEW_ZIP)  AS  RECV_ZIP  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ,  'B',  B.MNGEMSTR_NEW_ADDR1  ||  DECODE(B.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||B.MNGEMSTR_NEW_ADDR2)  ||  B.MNGEMSTR_REF_INFO    ,  B.PERMMSTR_NEW_ADDR1  ||  DECODE(B.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||B.PERMMSTR_NEW_ADDR2)  ||  B.PERMMSTR_REF_INFO)  AS  RECV_ADDR  ,  A.MONPRNCFEE  AS  USE_AMT  ,  A.TOT_DEMD_AMT  -  A.MONPRNCFEE  AS  NONPY_AMT  ,  A.TOT_ADDT_AMT  AS  ADDT_AMT  ,  A.TOT_EADDT_AMT  AS  EXT_ADDT_AMT  ,  A.TOT_DEMD_AMT  AS  DEMD_AMT  ,  C.BIPLC_NM  AS  BRAN_NM  ,  C.ADDR  ||  '  '||  C.HNM  AS  BRAN_ADDR  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_TEL  ,  C.POST_NUM  AS  BRAN_ZIP  ,  A.START_YRMN  ||  '  ~  '  ||  A.END_YRMN  AS  YRMN  ,   \n";
        query +=" (SELECT  DECODE(COUNT(*),0,0,1)  FROM  GIBU.TBRA_DISP_HISTY  WHERE  UPSO_CD  =  A.UPSO_CD)  AS  CK_SEND  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  A.DEMD_GBN  =  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO)   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  B.NEW_DAY  IS  NULL   \n";
        query +=" AND  NVL(B.PAPER_CANC_YN,  '  ')  <>  'Y'   \n";
        query +=" AND  B.BRAN_CD  =  C.GIBU(+)   \n";
        query +=" AND  TO_CHAR(B.INS_DATE,'YYYYMM')  =  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,'YYYYMM'),  -2),'YYYYMM') ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$use_appr_req_list
    //##**$$print_history
    /* * 프로그램명 : bra08_s03
    * 작성자 : 서정재
    * 작성일 : 2009/09/08
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLprint_history(DOBJ dobj)
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
            dobj  = CALLprint_history_MPD4(Conn, dobj);           //  건별처리
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
    public DOBJ CTLprint_history( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLprint_history_MPD4(Conn, dobj);           //  건별처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 건별처리
    public DOBJ CALLprint_history_MPD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD4");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dobj.getRetObject("S").getRecord().get("CK_SEND").equals("0"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLprint_history_INS1(Conn, dobj);           //  출력이력남기기
            }
        }
        return dobj;
    }
    // 출력이력남기기
    public DOBJ CALLprint_history_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLprint_history_INS1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLprint_history_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String DISP_NUM ="";        //발송 번호
        String INS_DATE ="";        //등록 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   DISP_DAY = dvobj.getRecord().get("DISP_DAY");   //발송 일자
        String   DISP_GBN ="B";   //발송 구분
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_DISP_HISTY (INS_DATE, INSPRES_ID, DISP_DAY, UPSO_CD, DISP_GBN, DISP_NUM)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :DISP_DAY , :UPSO_CD , :DISP_GBN , TO_CHAR(SYSDATE,'YYYYMMDD'))";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DISP_DAY", DISP_DAY);               //발송 일자
        sobj.setString("DISP_GBN", DISP_GBN);               //발송 구분
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$print_history
    //##**$$end
}
