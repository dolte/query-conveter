
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s25
{
    public bra01_s25()
    {
    }
    //##**$$get_usim_list
    /*
    */
    public DOBJ CTLget_usim_list(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("BRAN_CD").equals("AL"))
            {
                dobj  = CALLget_usim_list_SEL1(Conn, dobj);           //  전체지부 조회
                dobj  = CALLget_usim_list_MRG5( dobj);        //  머지
            }
            else
            {
                dobj  = CALLget_usim_list_SEL3(Conn, dobj);           //  지부 선택시
                dobj  = CALLget_usim_list_MRG5( dobj);        //  머지
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
    public DOBJ CTLget_usim_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("BRAN_CD").equals("AL"))
        {
            dobj  = CALLget_usim_list_SEL1(Conn, dobj);           //  전체지부 조회
            dobj  = CALLget_usim_list_MRG5( dobj);        //  머지
        }
        else
        {
            dobj  = CALLget_usim_list_SEL3(Conn, dobj);           //  지부 선택시
            dobj  = CALLget_usim_list_MRG5( dobj);        //  머지
        }
        return dobj;
    }
    // 전체지부 조회
    public DOBJ CALLget_usim_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_usim_list_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_usim_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL2 = dobj.getRetObject("S").getRecord().get("SERIAL2");   //로그기시리얼번호2
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   SERIAL1 = dobj.getRetObject("S").getRecord().get("SERIAL1");   //로그기시리얼번호1
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BB.UPSO_CD , DECODE(BB.BRAN_CD, NULL, '', '', '', (SELECT DEPT_NM  ";
        query +=" FROM INSA.TCPM_DEPT  ";
        query +=" WHERE GIBU = BB.BRAN_CD)) AS BRAN_NM , BB.UPSO_NM , DECODE(BB.UPSO_CD, NULL, '', '', '', GIBU.FT_GET_BSTYPGRAD_NM(BB.UPSO_CD, '')) AS GRAD_NM , AA.BSCON_CD , DECODE(BB.UPSO_CD, NULL, '', '','' , FIDU.GET_STAFF_NM(BB.STAFF_CD)) AS STAFF_NM , AA.SERIAL_NO , (SELECT CO_NAME  ";
        query +=" FROM LOG.KDS_CODE  ";
        query +=" WHERE CO_TYPE = '08'  ";
        query +=" AND CO_TYPE || CO_CODE = AA.CO_PARING) AS CO_PARING , AA.SEQ , AA.TEL_NO , AA.MODEM_NO , AA.USIM_NO , AA.USIM_MODEL , AA.MODEM_KIND , AA.BIGO , AA.INS_DATE  ";
        query +=" FROM (  ";
        query +=" SELECT A.SERIAL_NO , A.CO_PARING , B.SEQ , B.TEL_NO , B.MODEM_NO , B.USIM_NO , B.USIM_MODEL , B.MODEM_KIND , B.BIGO , B.INS_DATE , (  ";
        query +=" SELECT --+ INDEX_DESC (KDS_SHOPROOM PK_KDS_SM_SEQ) \n UPSO_CD  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE SERIAL_NO = A.SERIAL_NO  ";
        query +=" AND CO_STATUS = '07001'  ";
        query +=" AND ROWNUM = 1 ) AS UPSO_CD , (  ";
        query +=" SELECT --+ INDEX_DESC (KDS_SHOPROOM PK_KDS_SM_SEQ) \n BSCON_CD  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE SERIAL_NO = A.SERIAL_NO  ";
        query +=" AND CO_STATUS = '07001'  ";
        query +=" AND ROWNUM = 1 ) AS BSCON_CD  ";
        query +=" FROM LOG.KDS_LOGCOLLECTOR A , (  ";
        query +=" SELECT Y.SERIAL_NO , Y.SEQ , Y.TEL_NO , Y.MODEM_NO , Y.USIM_NO , Y.USIM_MODEL , Y.MODEM_KIND , Y.BIGO , Y.INSPRES_ID , Y.INS_DATE  ";
        query +=" FROM (  ";
        query +=" SELECT SERIAL_NO, MAX(SEQ) AS SEQ  ";
        query +=" FROM LOG.KDS_USIM  ";
        query +=" GROUP BY SERIAL_NO ) X , LOG.KDS_USIM Y  ";
        query +=" WHERE X.SERIAL_NO = Y.SERIAL_NO  ";
        query +=" AND X.SEQ = Y.SEQ ) B  ";
        query +=" WHERE A.SERIAL_NO = B.SERIAL_NO(+) ) AA , GIBU.TBRA_UPSO BB  ";
        query +=" WHERE AA.UPSO_CD = BB.UPSO_CD(+)  ";
        if("2".equals(GBN))
        {
            query +=" AND '2' = :GBN  ";
            query +=" AND AA.SERIAL_NO = 'C038E0EB00000000' || UPPER(:SERIAL1)  ";
        }
        if("3".equals(GBN))
        {
            query +=" AND '3' = :GBN  ";
            query +=" AND AA.SERIAL_NO IN ('C038E0EB00000000' || UPPER(:SERIAL1), 'C038E0EB00000000' || UPPER(:SERIAL2))  ";
        }
        if("4".equals(GBN))
        {
            query +=" AND '4' = :GBN  ";
            query +=" AND AA.SERIAL_NO BETWEEN 'C038E0EB00000000' || UPPER(:SERIAL1)  ";
            query +=" AND 'C038E0EB00000000' || UPPER(:SERIAL2)  ";
        }
        query +=" ORDER BY AA.SERIAL_NO  ";
        sobj.setSql(query);
        sobj.setString("SERIAL2", SERIAL2);               //로그기시리얼번호2
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("SERIAL1", SERIAL1);               //로그기시리얼번호1
        return sobj;
    }
    // 머지
    public DOBJ CALLget_usim_list_MRG5(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG5");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL3","");
        rvobj.setName("MRG5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 지부 선택시
    public DOBJ CALLget_usim_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_usim_list_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_usim_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL2 = dobj.getRetObject("S").getRecord().get("SERIAL2");   //로그기시리얼번호2
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   SERIAL1 = dobj.getRetObject("S").getRecord().get("SERIAL1");   //로그기시리얼번호1
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BB.UPSO_CD , DECODE(BB.BRAN_CD, NULL, '', '', '', (SELECT DEPT_NM  ";
        query +=" FROM INSA.TCPM_DEPT  ";
        query +=" WHERE GIBU = BB.BRAN_CD)) AS BRAN_NM , BB.UPSO_NM , DECODE(BB.UPSO_CD, NULL, '', '', '', GIBU.FT_GET_BSTYPGRAD_NM(BB.UPSO_CD, '')) AS GRAD_NM , AA.BSCON_CD , DECODE(BB.UPSO_CD, NULL, '', '', '', FIDU.GET_STAFF_NM(BB.STAFF_CD)) AS STAFF_NM , AA.SERIAL_NO , (SELECT CO_NAME  ";
        query +=" FROM LOG.KDS_CODE  ";
        query +=" WHERE CO_TYPE = '08'  ";
        query +=" AND CO_TYPE || CO_CODE = AA.CO_PARING) AS CO_PARING , AA.SEQ , AA.TEL_NO , AA.MODEM_NO , AA.USIM_NO , AA.USIM_MODEL , AA.MODEM_KIND , AA.BIGO , AA.INS_DATE  ";
        query +=" FROM (  ";
        query +=" SELECT A.SERIAL_NO , A.CO_PARING , B.SEQ , B.TEL_NO , B.MODEM_NO , B.USIM_NO , B.USIM_MODEL , B.MODEM_KIND , B.BIGO , B.INS_DATE , (  ";
        query +=" SELECT --+ INDEX_DESC (KDS_SHOPROOM PK_KDS_SM_SEQ) \n UPSO_CD  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE SERIAL_NO = A.SERIAL_NO  ";
        query +=" AND CO_STATUS = '07001'  ";
        query +=" AND ROWNUM = 1 ) AS UPSO_CD , (  ";
        query +=" SELECT --+ INDEX_DESC (KDS_SHOPROOM PK_KDS_SM_SEQ) \n BSCON_CD  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE SERIAL_NO = A.SERIAL_NO  ";
        query +=" AND CO_STATUS = '07001'  ";
        query +=" AND ROWNUM = 1 ) AS BSCON_CD  ";
        query +=" FROM LOG.KDS_LOGCOLLECTOR A , (  ";
        query +=" SELECT Y.SERIAL_NO , Y.SEQ , Y.TEL_NO , Y.MODEM_NO , Y.USIM_NO , Y.USIM_MODEL , Y.MODEM_KIND , Y.BIGO , Y.INSPRES_ID , Y.INS_DATE  ";
        query +=" FROM (  ";
        query +=" SELECT SERIAL_NO, MAX(SEQ) AS SEQ  ";
        query +=" FROM LOG.KDS_USIM  ";
        query +=" GROUP BY SERIAL_NO ) X , LOG.KDS_USIM Y  ";
        query +=" WHERE X.SERIAL_NO = Y.SERIAL_NO  ";
        query +=" AND X.SEQ = Y.SEQ ) B  ";
        query +=" WHERE A.SERIAL_NO = B.SERIAL_NO(+) ) AA , GIBU.TBRA_UPSO BB  ";
        query +=" WHERE AA.UPSO_CD = BB.UPSO_CD(+)  ";
        query +=" AND BB.BRAN_CD = :BRAN_CD  ";
        if("2".equals(GBN))
        {
            query +=" AND '2' = :GBN  ";
            query +=" AND AA.SERIAL_NO = 'C038E0EB00000000' || UPPER(:SERIAL1)  ";
        }
        if("3".equals(GBN))
        {
            query +=" AND '3' = :GBN  ";
            query +=" AND AA.SERIAL_NO IN ('C038E0EB00000000' || UPPER(:SERIAL1), 'C038E0EB00000000' || UPPER(:SERIAL2))  ";
        }
        if("4".equals(GBN))
        {
            query +=" AND '4' = :GBN  ";
            query +=" AND AA.SERIAL_NO BETWEEN 'C038E0EB00000000' || UPPER(:SERIAL1)  ";
            query +=" AND 'C038E0EB00000000' || UPPER(:SERIAL2)  ";
        }
        query +=" ORDER BY AA.SERIAL_NO  ";
        sobj.setSql(query);
        sobj.setString("SERIAL2", SERIAL2);               //로그기시리얼번호2
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("SERIAL1", SERIAL1);               //로그기시리얼번호1
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$get_usim_list
    //##**$$get_usim_history
    /*
    */
    public DOBJ CTLget_usim_history(DOBJ dobj)
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
            dobj  = CALLget_usim_history_SEL1(Conn, dobj);           //  이력조회
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
    public DOBJ CTLget_usim_history( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_usim_history_SEL1(Conn, dobj);           //  이력조회
        return dobj;
    }
    // 이력조회
    public DOBJ CALLget_usim_history_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_usim_history_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_usim_history_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("S").getRecord().get("SERIAL_NO");   //제품번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SERIAL_NO  ,  SEQ  ,  TEL_NO  ,  MODEM_NO  ,  USIM_NO  ,  USIM_MODEL  ,  MODEM_KIND  ,  BIGO  ,  FIDU.GET_STAFF_NM(INSPRES_ID)  AS  INSPRES_ID  ,  TO_CHAR(INS_DATE,  'YYYY-MM-DD  HH24:MI:SS')  AS  INS_DATE  FROM  LOG.KDS_USIM  WHERE  SERIAL_NO  =  :SERIAL_NO  ORDER  BY  SEQ ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        return sobj;
    }
    //##**$$get_usim_history
    //##**$$save_usim
    /*
    */
    public DOBJ CTLsave_usim(DOBJ dobj)
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
            dobj  = CALLsave_usim_MIUD6(Conn, dobj);           //  중복체크용 루핑
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            if( dobj.getRetObject("ADD4").getRecordCnt() == 0)
            {
                dobj  = CALLsave_usim_MIUD15(Conn, dobj);           //  저장용분
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
    public DOBJ CTLsave_usim( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_usim_MIUD6(Conn, dobj);           //  중복체크용 루핑
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        if( dobj.getRetObject("ADD4").getRecordCnt() == 0)
        {
            dobj  = CALLsave_usim_MIUD15(Conn, dobj);           //  저장용분
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // 중복체크용 루핑
    public DOBJ CALLsave_usim_MIUD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD6");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_usim_SEL2(Conn, dobj);           //  중복조회
                dobj  = CALLsave_usim_ADD4( dobj);        //  기록
            }
        }
        return dobj;
    }
    // 중복조회
    public DOBJ CALLsave_usim_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsave_usim_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_usim_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TEL_NO = dobj.getRetObject("R").getRecord().get("TEL_NO");   //전화번호
        String   MODEM_NO = dobj.getRetObject("R").getRecord().get("MODEM_NO");   //모뎀번호
        String   SERIAL_NO = dobj.getRetObject("R").getRecord().get("SERIAL_NO");   //제품번호
        String   USIM_NO = dobj.getRetObject("R").getRecord().get("USIM_NO");   //유심번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :SERIAL_NO  ||  ',  '  ||  A.SERIAL_NO  AS  SERIAL_NO  FROM  LOG.KDS_USIM  A  ,  (   \n";
        query +=" SELECT  SERIAL_NO,  MAX(SEQ)  AS  SEQ  FROM  LOG.KDS_USIM  WHERE  SERIAL_NO  <>  :SERIAL_NO  GROUP  BY  SERIAL_NO  )  B  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  (A.TEL_NO  =  :TEL_NO   \n";
        query +=" OR  A.MODEM_NO  =  :MODEM_NO   \n";
        query +=" OR  A.USIM_NO  =  :USIM_NO) ";
        sobj.setSql(query);
        sobj.setString("TEL_NO", TEL_NO);               //전화번호
        sobj.setString("MODEM_NO", MODEM_NO);               //모뎀번호
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("USIM_NO", USIM_NO);               //유심번호
        return sobj;
    }
    // 기록
    public DOBJ CALLsave_usim_ADD4(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD4");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL2");          //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("ADD4");
        rvobj = wutil.getAddedVobj(dobj,"ADD4","SERIAL_NO", dvobj );
        rvobj.setName("ADD4");
        rvobj.Println("ADD4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 저장용분
    public DOBJ CALLsave_usim_MIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD15");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_usim_SEL14(Conn, dobj);           //  SEQ 획득
                dobj  = CALLsave_usim_INS19(Conn, dobj);           //  등록
            }
        }
        return dobj;
    }
    // SEQ 획득
    public DOBJ CALLsave_usim_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsave_usim_SEL14(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_usim_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("R").getRecord().get("SERIAL_NO");   //제품번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  SEQ  FROM  LOG.KDS_USIM  WHERE  SERIAL_NO  =  :SERIAL_NO ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        return sobj;
    }
    // 등록
    // 이력등록이므로, 수정할때마다 새로 등록한다
    public DOBJ CALLsave_usim_INS19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS19");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_usim_INS19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS19") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_usim_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   USIM_NO = dvobj.getRecord().get("USIM_NO");   //유심번호
        String   BIGO = dvobj.getRecord().get("BIGO");   //비고사항
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //제품번호
        String   USIM_MODEL = dvobj.getRecord().get("USIM_MODEL");   //유심모델
        String   MODEM_NO = dvobj.getRecord().get("MODEM_NO");   //모뎀번호
        String   TEL_NO = dvobj.getRecord().get("TEL_NO");   //전화번호
        String   MODEM_KIND = dvobj.getRecord().get("MODEM_KIND");   //모뎀종류
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        double   SEQ = dobj.getRetObject("SEL14").getRecord().getDouble("SEQ");   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_USIM (INS_DATE, MODEM_KIND, TEL_NO, INSPRES_ID, MODEM_NO, USIM_MODEL, SERIAL_NO, BIGO, USIM_NO, SEQ)  \n";
        query +=" values(SYSDATE, :MODEM_KIND , :TEL_NO , :INSPRES_ID , :MODEM_NO , :USIM_MODEL , :SERIAL_NO , :BIGO , :USIM_NO , :SEQ )";
        sobj.setSql(query);
        sobj.setString("USIM_NO", USIM_NO);               //유심번호
        sobj.setString("BIGO", BIGO);               //비고사항
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("USIM_MODEL", USIM_MODEL);               //유심모델
        sobj.setString("MODEM_NO", MODEM_NO);               //모뎀번호
        sobj.setString("TEL_NO", TEL_NO);               //전화번호
        sobj.setString("MODEM_KIND", MODEM_KIND);               //모뎀종류
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    //##**$$save_usim
    //##**$$end
}
