
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra08_s04
{
    public bra08_s04()
    {
    }
    //##**$$contr_term_list
    /* * 프로그램명 : bra08_s04
    * 작성자 : 서정재
    * 작성일 : 2009/11/13
    * 설명 :
    [테스트 데이타]
    -강북지부(A), 2008/07
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLcontr_term_list(DOBJ dobj)
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
            dobj  = CALLcontr_term_list_SEL1(Conn, dobj);           //  사용계약해지통보리스트
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
    public DOBJ CTLcontr_term_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcontr_term_list_SEL1(Conn, dobj);           //  사용계약해지통보리스트
        return dobj;
    }
    // 사용계약해지통보리스트
    public DOBJ CALLcontr_term_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcontr_term_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_term_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BRAN_CD  ,  C.BIPLC_NM  BRAN_NM  ,  B.UPSO_CD  ,  B.UPSO_NM  ,  A.INP_YRMN  ,  A.CONTR_TERM_DAY  ,  A.DISP_DAY  ,  DECODE(B.MAIL_RCPT,'U',  B.UPSO_NEW_ZIP,  'B',  B.MNGEMSTR_NEW_ZIP,  'O',  B.PERMMSTR_NEW_ZIP)  ZIP  ,  DECODE(B.MAIL_RCPT,'U',  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ,'B',  B.MNGEMSTR_NEW_ADDR1  ||  DECODE(B.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||B.MNGEMSTR_NEW_ADDR2)  ||  B.MNGEMSTR_REF_INFO    ,'O',  B.PERMMSTR_NEW_ADDR1  ||  DECODE(B.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||B.PERMMSTR_NEW_ADDR2)  ||  B.PERMMSTR_REF_INFO  )  ADDR  ,  DECODE(PAYPRES_GBN,'B',  MNGEMSTR_NM,  'O',  PERMMSTR_NM)  DAEPYO  ,  C.POST_NUM  BRAN_ZIP  ,  C.ADDR||'  '||C.HNM  BRAN_ADDR  ,  C.PHON_NUM  BRAN_TEL  ,  C.FAX_NUM  BRAN_FAX  ,  TO_CHAR((A.INS_DATE+7),'YYYYMMDD')  DEAD_DAY  FROM  GIBU.TBRA_CONTR_TERM  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.INP_YRMN  =  :YRMN   \n";
        query +=" AND  C.GIBU(+)  =  B.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$contr_term_list
    //##**$$contr_term_delete
    /*
    */
    public DOBJ CTLcontr_term_delete(DOBJ dobj)
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
            dobj  = CALLcontr_term_delete_DEL1(Conn, dobj);           //  삭제
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
    public DOBJ CTLcontr_term_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcontr_term_delete_DEL1(Conn, dobj);           //  삭제
        return dobj;
    }
    // 삭제
    public DOBJ CALLcontr_term_delete_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcontr_term_delete_DEL1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_term_delete_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INP_YRMN = dvobj.getRecord().get("INP_YRMN");   //입력 년월
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_CONTR_TERM  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and INP_YRMN=:INP_YRMN";
        sobj.setSql(query);
        sobj.setString("INP_YRMN", INP_YRMN);               //입력 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$contr_term_delete
    //##**$$contr_term_mng
    /* * 프로그램명 : bra08_s04
    * 작성자 : 서정재
    * 작성일 : 2009/11/25
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLcontr_term_mng(DOBJ dobj)
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
            dobj  = CALLcontr_term_mng_MPD9(Conn, dobj);
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
    public DOBJ CTLcontr_term_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcontr_term_mng_MPD9(Conn, dobj);
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    public DOBJ CALLcontr_term_mng_MPD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD9");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLcontr_term_mng_SEL11(Conn, dobj);           //  중복체크
                if( dobj.getRetObject("SEL11").getRecord().getDouble("CNT") == 0)
                {
                    dobj  = CALLcontr_term_mng_INS5(Conn, dobj);           //  등록
                }
            }
        }
        return dobj;
    }
    // 중복체크
    public DOBJ CALLcontr_term_mng_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcontr_term_mng_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_term_mng_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   INP_YRMN = dobj.getRetObject("R").getRecord().get("INP_YRMN");   //입력 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_CONTR_TERM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  INP_YRMN  =  :INP_YRMN ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("INP_YRMN", INP_YRMN);               //입력 년월
        return sobj;
    }
    // 등록
    public DOBJ CALLcontr_term_mng_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcontr_term_mng_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_term_mng_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   INP_YRMN = dvobj.getRecord().get("INP_YRMN");   //입력 년월
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CONTR_TERM_DAY = dvobj.getRecord().get("CONTR_TERM_DAY");   //계약 해지 일자
        String   DISP_DAY = dvobj.getRecord().get("DISP_DAY");   //발송 일자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_CONTR_TERM (INS_DATE, INSPRES_ID, DISP_DAY, CONTR_TERM_DAY, UPSO_CD, INP_YRMN)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :DISP_DAY , :CONTR_TERM_DAY , :UPSO_CD , :INP_YRMN )";
        sobj.setSql(query);
        sobj.setString("INP_YRMN", INP_YRMN);               //입력 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CONTR_TERM_DAY", CONTR_TERM_DAY);               //계약 해지 일자
        sobj.setString("DISP_DAY", DISP_DAY);               //발송 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$contr_term_mng
    //##**$$contr_term_add
    /* * 프로그램명 : bra08_s04
    * 작성자 : 서정재
    * 작성일 : 2009/08/19
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLcontr_term_add(DOBJ dobj)
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
            dobj  = CALLcontr_term_add_SEL1(Conn, dobj);           //  추가된업소정보조회
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
    public DOBJ CTLcontr_term_add( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcontr_term_add_SEL1(Conn, dobj);           //  추가된업소정보조회
        return dobj;
    }
    // 추가된업소정보조회
    public DOBJ CALLcontr_term_add_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcontr_term_add_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_term_add_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  DECODE(A.PAYPRES_GBN,'B',  A.MNGEMSTR_NM,  'O',  A.PERMMSTR_NM)  DAEPYO  ,  B.POST_NUM  BRAN_ZIP  ,  B.ADDR  ||  '  '||  B.HNM  BRAN_ADDR  ,  B.PHON_NUM  BRAN_TEL  ,  B.FAX_NUM  BRAN_FAX  ,  DECODE(A.MAIL_RCPT,'U',  A.UPSO_NEW_ZIP,  'B',  A.MNGEMSTR_NEW_ZIP,  'O',  A.PERMMSTR_NEW_ZIP)  ZIP  ,  DECODE(A.MAIL_RCPT,'U',  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  ,'B',  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO    ,'O',  A.PERMMSTR_NEW_ADDR1  ||  DECODE(A.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||A.PERMMSTR_NEW_ADDR2)  ||  A.PERMMSTR_REF_INFO  )  ADDR  FROM  GIBU.TBRA_UPSO  A  ,  INSA.TCPM_BIPLC  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.BRAN_CD  =  B.GIBU(+) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$contr_term_add
    //##**$$end
}
