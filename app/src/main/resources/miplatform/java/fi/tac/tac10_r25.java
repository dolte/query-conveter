
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r25
{
    public tac10_r25()
    {
    }
    //##**$$EventID29
    /*
    */
    public DOBJ CTLEventID29(DOBJ dobj)
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
            dobj  = CALLEventID29_SEL1(Conn, dobj);           //  SEL
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
    public DOBJ CTLEventID29( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLEventID29_SEL1(Conn, dobj);           //  SEL
        return dobj;
    }
    // SEL
    public DOBJ CALLEventID29_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID29_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID29_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //청구서번호
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_NUM,  A.BILL_NUM,  A.ISS_DAY,  A.BILL_KND,  B.CODE_NM  AS  BILL_KND_NM,  A.BILL_GBN,  C.CODE_NM  AS  BILL_GBN_NM,  A.BSCON_CD,  A.BIPLC_GBN,  A.BRAN_CD,  A.BSCON_NM,  A.SUPPPRES_NM,  A.BSCON_INS_NUM,  A.BSCON_FNM_NM,  A.BSCON_REPPRES_NM,  A.BSCON_POST_NUM,  A.BSCON_ADDR,  A.BSCON_BSCDTN,  A.BSCON_BSTYP,  A.SUPPPRES_CD,  A.SUPPPRES_INS_NUM,  A.SUPPPRES_FNM_NM,  A.SUPPPRES_REPPRES_NM,  A.SUPPPRES_ADDR,  A.SUPPPRES_BSCDTN,  A.SUPPPRES_BSTYP,  A.SUPPTEMP_AMT,  A.ATAX_AMT,  A.REMAK,  A.ISS_YN  FROM  FIDU.TTAC_BILL  A  ,FIDU.TENV_CODE  B  ,  FIDU.TENV_CODE  C  WHERE  1=1   \n";
        query +=" AND  B.HIGH_CD  =  '00283'   \n";
        query +=" AND  C.HIGH_CD  =  '00216'   \n";
        query +=" AND  A.BILL_KND  =  B.CODE_CD   \n";
        query +=" AND  A.BILL_GBN  =  C.CODE_CD   \n";
        query +=" AND  A.DEL_YN  IS  NULL   \n";
        query +=" AND  A.BILL_NUM  =  :BILL_NUM   \n";
        query +=" AND  A.DEMD_NUM  =  :DEMD_NUM  ORDER  BY  A.BSCON_INS_NUM ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    //##**$$EventID29
    //##**$$tac10_r25_sb1
    /*
    */
    public DOBJ CTLtac10_r25_sb1(DOBJ dobj)
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
            dobj  = CALLtac10_r25_sb1_SEL1(Conn, dobj);           //  매출 계산서
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
    public DOBJ CTLtac10_r25_sb1( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r25_sb1_SEL1(Conn, dobj);           //  매출 계산서
        return dobj;
    }
    // 매출 계산서
    public DOBJ CALLtac10_r25_sb1_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r25_sb1_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r25_sb1_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.BILL_NUM, A.ISS_DAY, DECODE(LENGTH(TRIM(A.BSCON_INS_NUM)),10,TRIM(A.BSCON_INS_NUM),NULL) AS BSCON_INS_NUM, A.BSCON_FNM_NM, A.SUPPTEMP_AMT, A.ATAX_AMT  ";
        query +=" FROM FIDU.TTAC_BILL A  ";
        query +=" WHERE BILL_KND IN ('1','3','4')  ";
        query +=" AND A.SUPPTEMP_AMT <> 0  ";
        query +=" AND A.DEL_YN IS NULL  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.BSCON_INS_NUM = :MB_CD  ";
        }
        query +=" AND A.ISS_DAY BETWEEN :FROM_YRMN  ";
        query +=" AND :TO_YRMN  ";
        query +=" ORDER BY BSCON_INS_NUM NULLS FIRST , A.ISS_DAY  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        return sobj;
    }
    //##**$$tac10_r25_sb1
    //##**$$EventID32
    /*
    */
    public DOBJ CTLEventID32(DOBJ dobj)
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
            dobj  = CALLEventID32_XIUD1(Conn, dobj);           //  이메일 전송 적용
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
    public DOBJ CTLEventID32( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLEventID32_XIUD1(Conn, dobj);           //  이메일 전송 적용
        return dobj;
    }
    // 이메일 전송 적용
    public DOBJ CALLEventID32_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLEventID32_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        rvobj.Println("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID32_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO amail.ems_mailqueue (seq, mail_code, to_email, to_name, from_email, from_name, subject, target_flag, map1, map2, map3, map4, map5, map_content, reg_date ) SELECT amail.ems_mailqueue_seq.nextval,'27',B.EMAIL_ADDR, B.HANMB_NM , 'acc1@komca.or.kr' , '한국음악저작권협회' , SUBSTR(:SUPP_YRMN,1,4)||'년'||SUBSTR(:SUPP_YRMN,5,2) ||'월'||' 매출 현황' , 'N' , 'I', B.MB_CD , SUBSTR(:SUPP_YRMN,1,4)||SUBSTR(:SUPP_YRMN,5,2), SUBSTR(:SUPP_YRMN,1,4)||'년 '||SUBSTR(:SUPP_YRMN,5,2)||'월', '', 'TEMP' , SYSDATE FROM FIDU.TMEM_MB B WHERE MB_CD =:MB_CD";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$EventID32
    //##**$$tac10_r25_sb3
    /*
    */
    public DOBJ CTLtac10_r25_sb3(DOBJ dobj)
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
            dobj  = CALLtac10_r25_sb3_SEL1(Conn, dobj);           //  외국단체별 매출 내역
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
    public DOBJ CTLtac10_r25_sb3( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r25_sb3_SEL1(Conn, dobj);           //  외국단체별 매출 내역
        return dobj;
    }
    // 외국단체별 매출 내역
    public DOBJ CALLtac10_r25_sb3_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r25_sb3_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r25_sb3_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.BILL_NUM, FIDU.GET_MB_NM(B.RIGHTPARTY_CD) AS SUPPPRES_FNM_NM, A.BSCON_FNM_NM, B.TITLE_NM, FIDU.GET_MDM_NM(B.MDM_CD) AS MDM_NM, B.SUPPTEMP_AMT, B.ISS_DAY  ";
        query +=" FROM FIDU.TTAC_BILL A, FIDU.TTAC_USEAPPRVBRE B  ";
        query +=" WHERE A.BILL_KND = '1'  ";
        query +=" AND A.DEMD_NUM = B.DEMD_NUM  ";
        query +=" AND A.BILL_NUM = B.BILL_NUM  ";
        query +=" AND A.ISS_DAY = B.ISS_DAY  ";
        query +=" AND A.BILL_KND = B.BILL_KND  ";
        query +=" AND SUBSTR(B.ISS_DAY,1,6) = :FROM_YRMN  ";
        query +=" AND B.RIGHTPARTY_CD IS NOT NULL  ";
        query +=" AND A.DEL_YN IS NULL  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND B.RIGHTPARTY_CD = :MB_CD  ";
        }
        query +=" ORDER BY B.ISS_DAY,A.BSCON_FNM_NM ,B.TITLE_NM  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        return sobj;
    }
    //##**$$tac10_r25_sb3
    //##**$$tac10_r25_sb2
    /*
    */
    public DOBJ CTLtac10_r25_sb2(DOBJ dobj)
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
            dobj  = CALLtac10_r25_sb2_SEL1(Conn, dobj);           //  출판사 세금계산서
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
    public DOBJ CTLtac10_r25_sb2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r25_sb2_SEL1(Conn, dobj);           //  출판사 세금계산서
        return dobj;
    }
    // 출판사 세금계산서
    public DOBJ CALLtac10_r25_sb2_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r25_sb2_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r25_sb2_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.BILL_NUM, A.SUPPPRES_FNM_NM, A.SUPPPRES_CD, A.BSCON_FNM_NM, A.SUPPPRES_INS_NUM, MAX(B.TITLE_NM) TITLE_NM, MAX(FIDU.GET_MDM_NM(B.MDM_CD)) AS MDM_NM, SUM(B.SUPPTEMP_AMT) SUPPTEMP_AMT, SUM(B.ATAX_AMT) ATAX_AMT, SUM(NVL(B.SUPPTEMP_AMT,0)+NVL(B.ATAX_AMT,0)) AS TOT_AMT, B.ISS_DAY, A.DEMD_NUM, C.EMAIL_ADDR  ";
        query +=" FROM FIDU.TTAC_BILL A, FIDU.TTAC_USEAPPRVBRE B, FIDU.TMEM_MB C  ";
        query +=" WHERE A.BILL_KND = '2'  ";
        query +=" AND A.DEMD_NUM = B.DEMD_NUM  ";
        query +=" AND A.BILL_NUM = B.BILL_NUM  ";
        query +=" AND A.SUPPPRES_CD = C.MB_CD(+)  ";
        query +=" AND A.ISS_DAY = B.ISS_DAY  ";
        query +=" AND A.BILL_KND = B.BILL_KND  ";
        query +=" AND B.ISS_DAY LIKE :FROM_YRMN||'%'  ";
        query +=" AND A.DEL_YN IS NULL  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.SUPPPRES_INS_NUM = :MB_CD  ";
        }
        query +=" GROUP BY A.BILL_NUM, A.SUPPPRES_FNM_NM, A.BSCON_FNM_NM, B.ISS_DAY, A.SUPPPRES_CD, A.SUPPPRES_INS_NUM, A.DEMD_NUM, C.EMAIL_ADDR  ";
        query +=" ORDER BY A.SUPPPRES_INS_NUM,B.ISS_DAY  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        return sobj;
    }
    //##**$$tac10_r25_sb2
    //##**$$end
}
