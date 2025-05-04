
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra08_s05
{
    public bra08_s05()
    {
    }
    //##**$$legal_list
    /* * 프로그램명 : bra08_s05
    * 작성자 : 서정재
    * 작성일 : 2009/09/27
    * 설명 :
    [테스트 데이타]
    -강북지부(A), 2006.02
    * 수정일1: 2010/03/09
    * 수정자 :
    * 수정내용 : 받는사람 주소는 무조건 업소주소로 출력될수 있게 (박창순T)
    */
    public DOBJ CTLlegal_list(DOBJ dobj)
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
            dobj  = CALLlegal_list_SEL1(Conn, dobj);           //  법적절차착수통보리스트
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
    public DOBJ CTLlegal_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlegal_list_SEL1(Conn, dobj);           //  법적절차착수통보리스트
        return dobj;
    }
    // 법적절차착수통보리스트
    public DOBJ CALLlegal_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlegal_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BPAP_DAY  ,  A.UPSO_CD  ,  A.BPAP_GBN  ,  B.UPSO_NM  ,  DECODE(B.PAYPRES_GBN,  'B',  B.MNGEMSTR_NM,  B.PERMMSTR_NM)  AS  RECV_NM  ,  A.START_YRMN  ||  '01'  AS  START_YRMN  ,  A.END_YRMN  ||  '01'  AS  END_YRMN  ,  A.NONPY_AMT  ,  A.TOT_ADDT_AMT  +  A.TOT_EADDT_AMT  AS  TOT_ADDT_AMT  ,  A.TOT_DEMD_AMT  ,  LEGAL_PRNT_YN  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  AS  RECV_ADDR  ,  TO_DATE(A.BPAP_DAY)+  4  AS  DEADLINE  ,  C.BIPLC_NM  AS  BRAN_NM  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_PHON  ,   \n";
        query +=" (SELECT  IPPBX_USER_ID  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_FAX  ,  DECODE(B.BRAN_CD,  'A',  '국민은행',  'B',  '국민은행',  'C',  '국민은행',  'E'  ,  '국민은행'  ,  'F'  ,  '국민은행'  ,  'G'  ,  '농협'  ,  'H'  ,  '농협'  ,  'I'  ,  '농협'  ,  'J'  ,  '농협'  ,  'K'  ,  '농협'  ,  'L'  ,  '국민은행'  ,  'M'  ,  '농협'  ,  'N'  ,  '부산은행'  ,  'O'  ,  '농협')  BANK  ,  DECODE(B.BRAN_CD,  'A'  ,  '695037-01-001228'  ,  'B'  ,  '695037-01-001257'  ,  'C'  ,  '695037-01-001231'  ,  'E'  ,  '695037-01-001260'  ,  'F'  ,  '695037-01-001244'  ,  'G'  ,  '209-01-581021'  ,  'H'  ,  '311-01-155951'  ,  'I'  ,  '311-01-155951'  ,  'J'  ,  '511-01-073417'  ,  'K'  ,  '661-01-033882'  ,  'L'  ,  '632-01-0046-816'  ,  'M'  ,  '815135-51-018283'  ,  'N'  ,  '131-01-000342-2'  ,  'O'  ,  '901017-51-011928')  AS  ACCT_NO  ,  C.ADDR||'  '||C.HNM  AS  BRAN_ADDR  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NM,  '')  AS  R_UPSO_NM  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ZIP,  'B',  B.MNGEMSTR_NEW_ZIP,  'O',  B.PERMMSTR_NEW_ZIP)  AS  POST_NO  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ,  'B',  B.MNGEMSTR_NEW_ADDR1  ||  DECODE(B.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||B.MNGEMSTR_NEW_ADDR2)  ||  B.MNGEMSTR_REF_INFO    ,  'O',  B.PERMMSTR_NEW_ADDR1  ||  DECODE(B.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||B.PERMMSTR_NEW_ADDR2)  ||  B.PERMMSTR_REF_INFO)  AS  ADDR  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  WHERE  A.BPAP_GBN  =  '4'   \n";
        query +=" AND  A.BPAP_DAY  BETWEEN  :YRMN||'01'   \n";
        query +=" AND  :YRMN||'31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.GIBU(+)  =  B.BRAN_CD  ORDER  BY  B.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$legal_list
    //##**$$legal_delete
    /* * 프로그램명 : bra08_s05
    * 작성자 : 서정재
    * 작성일 : 2009/08/28
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLlegal_delete(DOBJ dobj)
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
            dobj  = CALLlegal_delete_DEL1(Conn, dobj);           //  삭제
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
    public DOBJ CTLlegal_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlegal_delete_DEL1(Conn, dobj);           //  삭제
        return dobj;
    }
    // 삭제
    public DOBJ CALLlegal_delete_DEL1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLlegal_delete_DEL1(dobj, dvobj);
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
    private SQLObject SQLlegal_delete_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   BPAP_GBN = dvobj.getRecord().get("BPAP_GBN");   //최고서 구분
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //최고서 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BPAP_PRNT_HISTY  \n";
        query +=" where BPAP_DAY=:BPAP_DAY  \n";
        query +=" and BPAP_GBN=:BPAP_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BPAP_GBN", BPAP_GBN);               //최고서 구분
        sobj.setString("BPAP_DAY", BPAP_DAY);               //최고서 일자
        return sobj;
    }
    //##**$$legal_delete
    //##**$$legal_insert
    /* * 프로그램명 : bra08_s05
    * 작성자 : 박태현
    * 작성일 : 2009/09/28
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLlegal_insert(DOBJ dobj)
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
            dobj  = CALLlegal_insert_MIUD2(Conn, dobj);           //  로우처리
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
    public DOBJ CTLlegal_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlegal_insert_MIUD2(Conn, dobj);           //  로우처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 로우처리
    public DOBJ CALLlegal_insert_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MIUD2");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLlegal_insert_SEL8(Conn, dobj);           //  중복체크
                if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") == 0)
                {
                    dobj  = CALLlegal_insert_INS1(Conn, dobj);           //  출력정보저장
                }
            }
        }
        return dobj;
    }
    // 중복체크
    public DOBJ CALLlegal_insert_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlegal_insert_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_insert_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BPAP_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //최고서 일자
        String   BPAP_GBN = dobj.getRetObject("R").getRecord().get("BPAP_GBN");   //최고서 구분
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  BPAP_DAY  =  :BPAP_DAY   \n";
        query +=" AND  BPAP_GBN  =  :BPAP_GBN ";
        sobj.setSql(query);
        sobj.setString("BPAP_DAY", BPAP_DAY);               //최고서 일자
        sobj.setString("BPAP_GBN", BPAP_GBN);               //최고서 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 출력정보저장
    public DOBJ CALLlegal_insert_INS1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLlegal_insert_INS1(dobj, dvobj);
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
    private SQLObject SQLlegal_insert_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //종료년월
        String INS_DATE ="";        //등록 일시
        String PAY_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");        //납부 일자
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //미납 금액
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //종료년월
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   BPAP_GBN = dvobj.getRecord().get("BPAP_GBN");   //최고서 구분
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //총 청구 금액
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //총 가산 금액
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //최고서 일자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        int   LEGAL_PRNT_YN = 0;   //법적절차출력여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BPAP_PRNT_HISTY (INS_DATE, INSPRES_ID, BPAP_DAY, LEGAL_PRNT_YN, TOT_ADDT_AMT, TOT_DEMD_AMT, BPAP_GBN, UPSO_CD, END_YRMN, NONPY_AMT, START_YRMN)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BPAP_DAY , :LEGAL_PRNT_YN , :TOT_ADDT_AMT , :TOT_DEMD_AMT , :BPAP_GBN , :UPSO_CD , SUBSTR(:END_YRMN_1,1,6), :NONPY_AMT , SUBSTR(:START_YRMN_1,1,6))";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //미납 금액
        sobj.setString("END_YRMN_1", END_YRMN_1);               //종료년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BPAP_GBN", BPAP_GBN);               //최고서 구분
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //총 청구 금액
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //총 가산 금액
        sobj.setString("BPAP_DAY", BPAP_DAY);               //최고서 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setInt("LEGAL_PRNT_YN", LEGAL_PRNT_YN);               //법적절차출력여부
        return sobj;
    }
    //##**$$legal_insert
    //##**$$legal_prnt_insert
    /* * 프로그램명 : bra08_s05
    * 작성자 : 박태현
    * 작성일 : 2009/09/28
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLlegal_prnt_insert(DOBJ dobj)
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
            dobj  = CALLlegal_prnt_insert_MPD4(Conn, dobj);           //  건별처리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLlegal_prnt_insert_SEL10(Conn, dobj);           //  법적절차착수통보리스트
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
    public DOBJ CTLlegal_prnt_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlegal_prnt_insert_MPD4(Conn, dobj);           //  건별처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLlegal_prnt_insert_SEL10(Conn, dobj);           //  법적절차착수통보리스트
        return dobj;
    }
    // 건별처리
    public DOBJ CALLlegal_prnt_insert_MPD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD4");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MPD4");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().getInt("LEGAL_PRNT_YN") == 0)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLlegal_prnt_insert_SEL6(Conn, dobj);           //  출력여부확인
                if( dobj.getRetObject("SEL6").getRecord().getDouble("CNT") == 1)
                {
                    dobj  = CALLlegal_prnt_insert_UPD9(Conn, dobj);           //  출력정보수정
                    dobj  = CALLlegal_prnt_insert_SEL20(Conn, dobj);           //  방문등록의 SEQ획득
                    dobj  = CALLlegal_prnt_insert_INS18(Conn, dobj);           //  업소방문등록 기록
                    dobj  = CALLlegal_prnt_insert_INS19(Conn, dobj);           //  업소방문 메모등록
                }
                else
                {
                    dobj  = CALLlegal_prnt_insert_INS1(Conn, dobj);           //  출력정보저장
                    dobj  = CALLlegal_prnt_insert_SEL11(Conn, dobj);           //  방문등록의 SEQ획득
                    dobj  = CALLlegal_prnt_insert_INS10(Conn, dobj);           //  업소방문등록 기록
                    dobj  = CALLlegal_prnt_insert_INS13(Conn, dobj);           //  업소방문 메모등록
                }
            }
            else
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLlegal_prnt_insert_SEL15(Conn, dobj);           //  방문등록 대상찾기
                dobj  = CALLlegal_prnt_insert_INS14(Conn, dobj);           //  업소방문 메모등록
            }
        }
        return dobj;
    }
    // 출력여부확인
    public DOBJ CALLlegal_prnt_insert_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlegal_prnt_insert_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BPAP_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //최고서 일자
        String   BPAP_GBN = dobj.getRetObject("R").getRecord().get("BPAP_GBN");   //최고서 구분
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  BPAP_DAY  =  :BPAP_DAY   \n";
        query +=" AND  BPAP_GBN  =  :BPAP_GBN ";
        sobj.setSql(query);
        sobj.setString("BPAP_DAY", BPAP_DAY);               //최고서 일자
        sobj.setString("BPAP_GBN", BPAP_GBN);               //최고서 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 방문등록 대상찾기
    public DOBJ CALLlegal_prnt_insert_SEL15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL15");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL15");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlegal_prnt_insert_SEL15(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_SEL15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //방문 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(VISIT_SEQ)  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  'L' ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소방문 메모등록
    public DOBJ CALLlegal_prnt_insert_INS14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_INS14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_INS14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //등록자 ID
        String INS_DATE ="";        //등록 일시
        String REMAK ="";        //비고
        int  VISIT_NUM = 0;        //방문 번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   VISIT_NUM_3 = dobj.getRetObject("SEL15").getRecord().get("VISIT_SEQ");   //방문 번호
        String   VISIT_NUM_2 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //방문 번호
        String   VISIT_NUM_1 = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //방문 번호
        String   JOB_GBN ="L";   //업무 구분
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //방문 일자
        int   VISIT_SEQ = dobj.getRetObject("SEL15").getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , :VISIT_DAY , :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM), 0) + 1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = :VISIT_NUM_1 AND UPSO_CD = :VISIT_NUM_2 AND VISIT_SEQ = :VISIT_NUM_3 AND JOB_GBN = 'L'), :UPSO_CD , '출력일 : ' || to_char(SYSDATE, 'YYYYMMDD'))";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("VISIT_NUM_3", VISIT_NUM_3);               //방문 번호
        sobj.setString("VISIT_NUM_2", VISIT_NUM_2);               //방문 번호
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //방문 번호
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 출력정보수정
    public DOBJ CALLlegal_prnt_insert_UPD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_UPD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_UPD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   BPAP_GBN = dvobj.getRecord().get("BPAP_GBN");   //최고서 구분
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //최고서 일자
        int   LEGAL_PRNT_YN = 1;   //법적절차출력여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BPAP_PRNT_HISTY  \n";
        query +=" set LEGAL_PRNT_YN=:LEGAL_PRNT_YN  \n";
        query +=" where BPAP_DAY=:BPAP_DAY  \n";
        query +=" and BPAP_GBN=:BPAP_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BPAP_GBN", BPAP_GBN);               //최고서 구분
        sobj.setString("BPAP_DAY", BPAP_DAY);               //최고서 일자
        sobj.setInt("LEGAL_PRNT_YN", LEGAL_PRNT_YN);               //법적절차출력여부
        return sobj;
    }
    // 방문등록의 SEQ획득
    public DOBJ CALLlegal_prnt_insert_SEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL20");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL20");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlegal_prnt_insert_SEL20(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_SEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("BPAP_DAY");   //방문 일자
        String   JOB_GBN ="E";   //업무 구분
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소방문등록 기록
    public DOBJ CALLlegal_prnt_insert_INS18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_INS18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String VISIT_TIME ="";        //방문 시간
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CONSPRES ="[수신자] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //상담자
        String   JOB_GBN ="L";   //업무 구분
        String   REMAK ="법적절차착수통보";   //비고
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //방문 일자
        int   VISIT_SEQ = dobj.getRetObject("SEL20").getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, TO_CHAR(SYSDATE, 'HH24MI'), :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CONSPRES", CONSPRES);               //상담자
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 업소방문 메모등록
    public DOBJ CALLlegal_prnt_insert_INS19(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLlegal_prnt_insert_INS19(dobj, dvobj);
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
    private SQLObject SQLlegal_prnt_insert_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //등록자 ID
        String INS_DATE ="";        //등록 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN ="L";   //업무 구분
        String   REMAK ="납부일자 : "+dobj.getRetObject("R").getRecord().get("ADD_DATE");   //비고
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //방문 일자
        int   VISIT_NUM = 1;   //방문 번호
        int   VISIT_SEQ = dobj.getRetObject("SEL20").getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //방문 번호
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 출력정보저장
    public DOBJ CALLlegal_prnt_insert_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_INS1(dobj, dvobj);
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
    private SQLObject SQLlegal_prnt_insert_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //종료년월
        String INS_DATE ="";        //등록 일시
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //미납 금액
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //종료년월
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   BPAP_GBN = dvobj.getRecord().get("BPAP_GBN");   //최고서 구분
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //총 청구 금액
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //총 가산 금액
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //최고서 일자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        int   LEGAL_PRNT_YN = 1;   //법적절차출력여부
        String   PAY_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //납부 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BPAP_PRNT_HISTY (INS_DATE, INSPRES_ID, BPAP_DAY, LEGAL_PRNT_YN, TOT_ADDT_AMT, TOT_DEMD_AMT, BPAP_GBN, UPSO_CD, PAY_DAY, END_YRMN, NONPY_AMT, START_YRMN)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BPAP_DAY , :LEGAL_PRNT_YN , :TOT_ADDT_AMT , :TOT_DEMD_AMT , :BPAP_GBN , :UPSO_CD , :PAY_DAY , SUBSTR(:END_YRMN_1,1,6), :NONPY_AMT , SUBSTR(:START_YRMN_1,1,6))";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //미납 금액
        sobj.setString("END_YRMN_1", END_YRMN_1);               //종료년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BPAP_GBN", BPAP_GBN);               //최고서 구분
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //총 청구 금액
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //총 가산 금액
        sobj.setString("BPAP_DAY", BPAP_DAY);               //최고서 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setInt("LEGAL_PRNT_YN", LEGAL_PRNT_YN);               //법적절차출력여부
        sobj.setString("PAY_DAY", PAY_DAY);               //납부 일자
        return sobj;
    }
    // 방문등록의 SEQ획득
    public DOBJ CALLlegal_prnt_insert_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlegal_prnt_insert_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("BPAP_DAY");   //방문 일자
        String   JOB_GBN ="E";   //업무 구분
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소방문등록 기록
    public DOBJ CALLlegal_prnt_insert_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_INS10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String VISIT_TIME ="";        //방문 시간
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CONSPRES ="[수신자] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //상담자
        String   JOB_GBN ="L";   //업무 구분
        String   REMAK ="법적절차착수통보";   //비고
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //방문 일자
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, TO_CHAR(SYSDATE, 'HH24MI'), :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CONSPRES", CONSPRES);               //상담자
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 업소방문 메모등록
    public DOBJ CALLlegal_prnt_insert_INS13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_INS13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_INS13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //등록자 ID
        String INS_DATE ="";        //등록 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN ="L";   //업무 구분
        String   REMAK ="납부일자 : "+dobj.getRetObject("R").getRecord().get("ADD_DATE");   //비고
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //방문 일자
        int   VISIT_NUM = 1;   //방문 번호
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //방문 번호
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 법적절차착수통보리스트
    public DOBJ CALLlegal_prnt_insert_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlegal_prnt_insert_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BPAP_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  DECODE(B.PAYPRES_GBN,  'B',  B.MNGEMSTR_NM,  B.PERMMSTR_NM)  RECV_NM  ,  A.START_YRMN||'01'  START_YRMN  ,  A.END_YRMN||'01'  END_YRMN  ,  A.NONPY_AMT  ,  A.TOT_ADDT_AMT  +  A.TOT_EADDT_AMT  TOT_ADDT_AMT  ,  A.TOT_DEMD_AMT  ,  LEGAL_PRNT_YN  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ,  'B',  B.MNGEMSTR_NEW_ADDR1  ||  DECODE(B.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||B.MNGEMSTR_NEW_ADDR2)  ||  B.MNGEMSTR_REF_INFO    ,  B.PERMMSTR_NEW_ADDR1  ||  DECODE(B.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||B.PERMMSTR_NEW_ADDR2)  ||  B.PERMMSTR_REF_INFO  )  RECV_ADDR  ,  TO_DATE(A.BPAP_DAY)+  4  PRNT_DAY  ,  C.BIPLC_NM  BRAN_NM  ,  C.PHON_NUM  BRAN_PHON  ,  C.FAX_NUM  BRAN_FAX  ,  DECODE(B.BRAN_CD,  'A',  '국민은행','B',  '국민은행','C',  '국민은행',  'E'  ,  '국민은행'  ,  'F'  ,  '국민은행'  ,  'G'  ,  '신한은행'  ,  'H'  ,  '농협'  ,  'I'  ,  '농협'  ,  'J'  ,  '농협'  ,  'K'  ,  '농협'  ,  'L'  ,  '국민은행'  ,  'M'  ,  '농협'  ,  'N'  ,  '부산은행'  ,  'O'  ,  '농협')  BANK  ,  DECODE(B.BRAN_CD,  'A'  ,  '695037-01-001228'  ,  'B'  ,  '695037-01-001257'  ,  'C'  ,  '695037-01-001231'  ,  'E'  ,  '695037-01-001260'  ,  'F'  ,  '695037-01-001244'  ,  'G'  ,  '100-022-587700'  ,  'H'  ,  '311-01-155951'  ,  'I'  ,  '311-01-155951'  ,  'J'  ,  '511-01-073417'  ,  'K'  ,  '661-01-033882'  ,  'L'  ,  '632-01-0046-816'  ,  'M'  ,  '815135-51-018283'  ,  'N'  ,  '131-01-000342-2'  ,  'O'  ,  '901017-51-011928')  ACCT_NO  ,  C.ADDR||'  '||C.HNM  BRAN_ADDR  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  WHERE  A.BPAP_GBN  =  '4'   \n";
        query +=" AND  A.BPAP_DAY  BETWEEN  :YRMN||'01'   \n";
        query +=" AND  :YRMN||'31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.GIBU(+)  =  B.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$legal_prnt_insert
    //##**$$legal_add_init
    /* * 프로그램명 : bra08_s05
    * 작성자 : 서정재
    * 작성일 : 2009/11/24
    * 설명 :
    * 수정일1: 2010/03/09
    * 수정자 :
    * 수정내용 : 받는사람 주소는 무조건 업소주소로 출력될수 있게 (박창순T)
    */
    public DOBJ CTLlegal_add_init(DOBJ dobj)
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
            dobj  = CALLlegal_add_init_SEL1(Conn, dobj);           //  폐업여부확인
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 1000)
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="폐업된업소입니다.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
            }
            else
            {
                dobj  = CALLlegal_add_init_SEL7(Conn, dobj);           //  청구기간구하기
                dobj  = CALLlegal_add_init_OSP1(Conn, dobj);           //  청구 금액 생성
                dobj  = CALLlegal_add_init_SEL4(Conn, dobj);           //  추가할정보데이타
                if( dobj.getRetObject("SEL4").getRecord().getDouble("NONPY_AMT") == 0)
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="미납금이 없는 업소입니다.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
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
    public DOBJ CTLlegal_add_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlegal_add_init_SEL1(Conn, dobj);           //  폐업여부확인
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 1000)
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="폐업된업소입니다.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        else
        {
            dobj  = CALLlegal_add_init_SEL7(Conn, dobj);           //  청구기간구하기
            dobj  = CALLlegal_add_init_OSP1(Conn, dobj);           //  청구 금액 생성
            dobj  = CALLlegal_add_init_SEL4(Conn, dobj);           //  추가할정보데이타
            if( dobj.getRetObject("SEL4").getRecord().getDouble("NONPY_AMT") == 0)
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="미납금이 없는 업소입니다.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
            }
        }
        return dobj;
    }
    // 폐업여부확인
    public DOBJ CALLlegal_add_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlegal_add_init_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_add_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  CLSBS_YRMN  IS  NOT  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구기간구하기
    public DOBJ CALLlegal_add_init_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlegal_add_init_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_add_init_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,6)  START_YRMN  ,  DECODE(CLSBS_YRMN,  NULL,  TO_CHAR(SYSDATE,  'YYYYMM'),  CLSBS_YRMN)  END_YRMN  ,  UPSO_CD  ,  'O'  AS  CRET_GBN  ,  ''  AS  RECV_DAY  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD=  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 금액 생성
    // 해당 업소의 청구 금액를 생성하기 위한 프로시저 (GIBU.SP_GET_USE_AMT) 를 호출한다
    public DOBJ CALLlegal_add_init_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL7");         //청구기간구하기에서 생성시킨 OBJECT입니다.(CALLlegal_add_init_SEL7)
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","CRET_GBN","RECV_DAY"};
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
        String   spname ="GIBU.SP_GET_DEMD_AMT";
        int[]    intypes={12, 12, 12, 12, 12};;
        String[] outcolnms={"P_BSTYP_CD","P_UPSO_GRAD","P_MONPRNCFEE","P_DEMD_GBN","P_DEMD_MMCNT","P_TUSE_AMT","P_TADDT_AMT","P_TEADDT_AMT","P_DSCT_AMT","P_TDEMD_AMT"};;
        int[]    outtypes ={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.setName("OSP1");
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 추가할정보데이타
    public DOBJ CALLlegal_add_init_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlegal_add_init_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_add_init_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BPAP_DAY = dobj.getRetObject("S").getRecord().get("BPAP_DAY");   //최고서 일자
        double   TOT_ADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TADDT_AMT");   //총 가산 금액
        double   USE_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TUSE_AMT");   //사용 금액
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("SEL7").getRecord().get("END_YRMN");   //종료년월
        double   TOT_EADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TEADDT_AMT");   //총 중가산 금액
        String   START_YRMN = dobj.getRetObject("SEL7").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :BPAP_DAY  AS  BPAP_DAY  ,  A.UPSO_CD  ,  A.UPSO_NM  ,  DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_NM,  A.PERMMSTR_NM)  RECV_NM  ,  :START_YRMN  AS  START_YRMN  ,  :END_YRMN  AS  END_YRMN  ,  :USE_AMT  AS  NONPY_AMT  ,  (:TOT_ADDT_AMT  +  :TOT_EADDT_AMT)  AS  TOT_ADDT_AMT  ,  (:USE_AMT  +  :TOT_ADDT_AMT  +  :TOT_EADDT_AMT)  AS  TOT_DEMD_AMT  ,  0  AS  LEGAL_PRNT_YN  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  RECV_ADDR  ,  TO_DATE(:BPAP_DAY)  +  4  AS  DEADLINE  ,  B.BIPLC_NM  BRAN_NM  ,  B.PHON_NUM  BRAN_PHON  ,  B.FAX_NUM  BRAN_FAX  ,  DECODE(A.BRAN_CD,  'A',  '국민은행','B',  '국민은행','C',  '국민은행',  'E'  ,  '국민은행'  ,  'F'  ,  '국민은행'  ,  'G'  ,  '신한은행'  ,  'H'  ,  '농협'  ,  'I'  ,  '우체국'  ,  'J'  ,  '농협'  ,  'K'  ,  '농협'  ,  'L'  ,  '국민은행'  ,  'M'  ,  '농협'  ,  'N'  ,  '부산은행'  ,  'O'  ,  '농협')  BANK  ,  DECODE(A.BRAN_CD,  'A'  ,  '695037-01-001228'  ,  'B'  ,  '695037-01-001257'  ,  'C'  ,  '695037-01-001231'  ,  'E'  ,  '695037-01-001260'  ,  'F'  ,  '695037-01-001244'  ,  'G'  ,  '100-022-587700'  ,  'H'  ,  '311-01-155951'  ,  'I'  ,  '310037-01-003561'  ,  'J'  ,  '511-01-073417'  ,  'K'  ,  '661-01-033882'  ,  'L'  ,  '632-01-0046-816'  ,  'M'  ,  '815135-51-018283'  ,  'N'  ,  '131-01-000342-2'  ,  'O'  ,  '901017-51-011928')  ACCT_NO  ,  B.ADDR||'  '||B.HNM  BRAN_ADDR  FROM  GIBU.TBRA_UPSO  A  ,  INSA.TCPM_BIPLC  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NVL(A.PAPER_CANC_YN,  '  ')  <>  'Y'   \n";
        query +=" AND  B.GIBU(+)  =  A.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("BPAP_DAY", BPAP_DAY);               //최고서 일자
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //총 가산 금액
        sobj.setDouble("USE_AMT", USE_AMT);               //사용 금액
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setDouble("TOT_EADDT_AMT", TOT_EADDT_AMT);               //총 중가산 금액
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$legal_add_init
    //##**$$end
}
