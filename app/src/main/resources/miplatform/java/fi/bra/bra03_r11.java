
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_r11
{
    public bra03_r11()
    {
    }
    //##**$$auto_demd_rept
    /* * 프로그램명 : bra03_r11
    * 작성자 : 서정재
    * 작성일 : 2010/01/08
    * 설명    : 자동이체 영수증, 청구서를 출력한다.
    [출력조건] TBRA_DEMD_AUTO
    1. DEMD_YRMN(조회년월)에 영수내역이 있는 업소
    2. DEMD_YRMN(조회년월)에 영수내역이 있는 업소 && DEMD_YRMN+1월에 청구내역이 있는 업소
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLauto_demd_rept(DOBJ dobj)
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
            dobj  = CALLauto_demd_rept_SEL1(Conn, dobj);           //  자동이체청구서,영수증
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
    public DOBJ CTLauto_demd_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_demd_rept_SEL1(Conn, dobj);           //  자동이체청구서,영수증
        return dobj;
    }
    // 자동이체청구서,영수증
    public DOBJ CALLauto_demd_rept_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_demd_rept_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_demd_rept_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //자동 처리 구분
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  A.DEMD_YRMN  ,  TO_CHAR(ADD_MONTHS(TO_DATE(A.DEMD_YRMN,  'YYYYMM'),  1),  'YYYYMM')  AS  B_DEMD_YRMN  ,  B.CLIENT_NUM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.MONPRNCFEE  ,  (CASE  WHEN  (A.TOT_DEMD_AMT  -  A.MONPRNCFEE)  >  0  THEN  (A.TOT_DEMD_AMT  -  A.MONPRNCFEE)  ELSE  0  END)  AS  NONPY_AMT  ,  A.TOT_DEMD_AMT  ,  E.MONPRNCFEE  AS  B_MONPRNCFEE  ,  E.DSCT_AMT  AS  B_DSCT_AMT  ,  E.TOT_USE_AMT  AS  B_TOT_USE_AMT  ,  E.TOT_DEMD_AMT  AS  B_TOT_DEMD_AMT  ,  E.START_YRMN  AS  B_START_YRMN  ,  E.END_YRMN  AS  B_END_YRMN  ,  (E.TOT_DEMD_AMT  -  E.MONPRNCFEE)  AS  B_NONPY_AMT  ,  E.TOT_ADDT_AMT  +  E.TOT_EADDT_AMT  AS  ADDT_AMT  ,   \n";
        query +=" (SELECT  DECODE(BANK_NM,  NULL,  BANK_CD,  BANK_NM)  FROM  ACCT.TCAC_BANK_7  WHERE  BANK_CD  LIKE  SUBSTR(C.BANK_CD,  1,  3)  ||  '%'   \n";
        query +=" AND  ROWNUM  =  1)  AS  BANK_NM  ,  SUBSTR(C.AUTO_ACCNNUM,  1,  3)  AS  BANK_NO  ,  D.BIPLC_NM  AS  BRAN_NM  ,  D.ADDR  ||  D.HNM  AS  BRAN_ADDR  ,  H.IPPBX_INPHONE_NUM  AS  PHON_NUM2  ,  GIBU.FT_GET_PHONE_FORMAT(H.IPPBX_INPHONE_NUM)  AS  PHON_NUM  ,  D.POST_NUM  ,  B.UPSO_NM  AS  UPSO_NM  ,  B.UPSO_NM  AS  O_UPSO_NM  ,  DECODE(B.PAYPRES_GBN,  'B',  B.MNGEMSTR_NM,  B.PERMMSTR_NM)  AS  DAEPYO  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ZIP,  'B',  B.MNGEMSTR_NEW_ZIP,  'O',  B.PERMMSTR_NEW_ZIP)  AS  ZIP  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ,  'B',  B.MNGEMSTR_NEW_ADDR1  ||  DECODE(B.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  B.MNGEMSTR_NEW_ADDR2)  ||  B.MNGEMSTR_REF_INFO  ,  'O',  B.PERMMSTR_NEW_ADDR1  ||  DECODE(B.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  B.PERMMSTR_NEW_ADDR2)  ||  B.PERMMSTR_REF_INFO)  AS  ADDR  ,  NVL(A.PROC_GBN,  '1')  AS  PROC_GBN  ,  G.GBN  ,  B.EMAIL_ADDR  AS  EMAIL  ,  C.PWD  ,  REGEXP_REPLACE(DECODE(B.PAYPRES_GBN,  'B',  B.MNGEMSTR_HPNUM,  NVL(B.PERMMSTR_HPNUM,  B.MNGEMSTR_HPNUM)),  '[^0-9]',  '')  AS  CP_NUM  ,  GIBU.GET_BRAN_NM(A.BRAN_CD)  AS  DEPT_NM  ,  F.RECV_DAY  ,  F.REPT_AMT  FROM  GIBU.TBRA_DEMD_AUTO  A  ,  GIBU.TBRA_UPSO  B  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  BANK_CD  ,  AUTO_ACCNNUM  ,  GBN  ,  EMAIL  ,  PWD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BANK_CD  ,  AUTO_ACCNNUM  ,  GBN  ,  EMAIL  ,  SUBSTR(RESINUM,  LENGTH(RESINUM)  -  6)  AS  PWD  ,  RANK()  OVER(PARTITION  BY  UPSO_CD  ORDER  BY  AUTO_NUM  DESC)  AS  RN  FROM  GIBU.TBRA_UPSO_AUTO  )  WHERE  RN  =  1  )  C  ,  INSA.TCPM_BIPLC  D  ,  GIBU.TBRA_DEMD_AUTO  E  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  B.RECV_DAY  ,  A.REPT_AMT  FROM  GIBU.TBRA_REPT_AUTO  A,  GIBU.TBRA_NOTE  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  '01'   \n";
        query +=" AND  B.BRAN_CD  LIKE  :BRAN_CD  ||  '%'   \n";
        query +=" AND  B.NOTE_YRMN  =  :YRMN  )  F  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GBN  FROM  GIBU.TFMS_UPSO  WHERE  GBN  IS  NOT  NULL  )  G  ,  FIDU.TENV_MEMBER  H  WHERE  A.BRAN_CD  LIKE  :BRAN_CD  ||  '%'   \n";
        query +=" AND  A.DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  A.TRNF_RSLT  IS  NULL   \n";
        query +=" AND  A.TOT_DEMD_AMT  >  0   \n";
        query +=" AND  A.DEMD_GBN  =  '31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  E.DEMD_YRMN(+)  =  TO_CHAR(ADD_MONTHS(TO_DATE(  :YRMN,  'YYYYMM'),  1),  'YYYYMM')   \n";
        query +=" AND  E.BRAN_CD(+)  =  A.BRAN_CD   \n";
        query +=" AND  E.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  E.TRNF_RSLT(+)  IS  NULL   \n";
        query +=" AND  E.TOT_DEMD_AMT(+)  >  0   \n";
        query +=" AND  E.DEMD_GBN(+)  =  '31'   \n";
        query +=" AND  NVL(A.PROC_GBN,  '1')  LIKE  :PROC_GBN  ||  '%'   \n";
        query +=" AND  A.UPSO_CD  =  G.UPSO_CD   \n";
        query +=" AND  G.GBN  =  DECODE(  :GBN,  'all',  G.GBN,  :GBN)   \n";
        query +=" AND  B.PAPER_CANC_YN  =  'N'   \n";
        query +=" AND  A.UPSO_CD  =  F.UPSO_CD   \n";
        query +=" AND  B.STAFF_CD  =  H.USER_ID  ORDER  BY  A.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$auto_demd_rept
    //##**$$send_email
    /*
    */
    public DOBJ CTLsend_email(DOBJ dobj)
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
            dobj  = CALLsend_email_XIUD1(Conn, dobj);           //  자동메일 발송
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
    public DOBJ CTLsend_email( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsend_email_XIUD1(Conn, dobj);           //  자동메일 발송
        return dobj;
    }
    // 자동메일 발송
    public DOBJ CALLsend_email_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsend_email_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsend_email_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MAP1 = dobj.getRetObject("S").getRecord().get("MAP1");   //메일 MAP1
        String   MAP2 = dobj.getRetObject("S").getRecord().get("MAP2");   //메일 MAP2
        String   MAP3 = dobj.getRetObject("S").getRecord().get("MAP3");   //메일 MAP3
        String   MAP4 = dobj.getRetObject("S").getRecord().get("MAP4");   //메일 MAP4
        String   MAP5 = dobj.getRetObject("S").getRecord().get("MAP5");   //메일 MAP5
        String   MAP_CONTENT = dobj.getRetObject("S").getRecord().get("MAP_CONTENT");   //컨텐츠
        String   TO_EMAIL = dobj.getRetObject("S").getRecord().get("TO_EMAIL");   //수신자 이메일
        String   TO_NAME = dobj.getRetObject("S").getRecord().get("TO_NAME");   //수신자 이름
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO amail.ems_mailqueue (seq, mail_code, to_email, to_name, from_email, from_name, subject, target_flag, map1, map2, map3, map4, map5, map_content, reg_date ) SELECT amail.ems_mailqueue_seq.nextval , '24' , :TO_EMAIL , :TO_NAME , 'local@komca.or.kr' , '한국음악저작권협회' , :MAP1 || '자동이체 청구서' , 'N' , :MAP1 , :MAP2 , :MAP3 , :MAP4 , :MAP5 , :MAP_CONTENT , SYSDATE FROM DUAL";
        sobj.setSql(query);
        sobj.setString("MAP1", MAP1);               //메일 MAP1
        sobj.setString("MAP2", MAP2);               //메일 MAP2
        sobj.setString("MAP3", MAP3);               //메일 MAP3
        sobj.setString("MAP4", MAP4);               //메일 MAP4
        sobj.setString("MAP5", MAP5);               //메일 MAP5
        sobj.setString("MAP_CONTENT", MAP_CONTENT);               //컨텐츠
        sobj.setString("TO_EMAIL", TO_EMAIL);               //수신자 이메일
        sobj.setString("TO_NAME", TO_NAME);               //수신자 이름
        return sobj;
    }
    //##**$$send_email
    //##**$$end
}
