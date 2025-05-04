
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s03
{
    public bra01_s03()
    {
    }
    //##**$$upso_amt_change
    /* * 프로그램명 : bra01_s03
    * 작성자 : 서정재
    * 작성일 : 2009/09/22
    * 설명 : 사용료 변경시 적용일자는 최종청구년월(CHECK_YRMN) 이후부터 가능하다.
    * 수정일1: 2010/02/24
    * 수정자 :
    * 수정내용 :사용료 변경시 적용년월의 청구데이타가 있는지 마감체크하지 말고 사용료 변경은 아무때나 가능하게 하자.
    (지부 팀장님-2010/02/24)
    */
    public DOBJ CTLupso_amt_change(DOBJ dobj)
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
            dobj  = CALLupso_amt_change_MIUD1(Conn, dobj);           //  사용료변경관리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_amt_change_MIUD29(Conn, dobj);           //  노래방정보
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_amt_change_SEL9(Conn, dobj);           //  업소사용료리스트
            if( dobj.getRetObject("SEL9").firstRecord().get("ROWNUM").equals("1") && dobj.getRetObject("SEL9").firstRecord().get("BSTYP_CD").equals("o"))
            {
                dobj  = CALLupso_amt_change_SEL19(Conn, dobj);           //  노래방상세정보
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
    public DOBJ CTLupso_amt_change( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_amt_change_MIUD1(Conn, dobj);           //  사용료변경관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_amt_change_MIUD29(Conn, dobj);           //  노래방정보
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_amt_change_SEL9(Conn, dobj);           //  업소사용료리스트
        if( dobj.getRetObject("SEL9").firstRecord().get("ROWNUM").equals("1") && dobj.getRetObject("SEL9").firstRecord().get("BSTYP_CD").equals("o"))
        {
            dobj  = CALLupso_amt_change_SEL19(Conn, dobj);           //  노래방상세정보
        }
        return dobj;
    }
    // 사용료변경관리
    public DOBJ CALLupso_amt_change_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_amt_change_SEL7(Conn, dobj);           //  CHG_NUM생성
                dobj  = CALLupso_amt_change_INS6(Conn, dobj);           //  사용료변경
                if( dobj.getRetObject("R").getRecord().get("BSTYP_CD").equals("o"))
                {
                    dobj  = CALLupso_amt_change_UPD40(Conn, dobj);           //  업소정보변경
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_amt_change_UPD7(Conn, dobj);           //  사용료수정
                if( dobj.getRetObject("R").getRecord().get("BSTYP_CD").equals("o"))
                {
                    dobj  = CALLupso_amt_change_UPD42(Conn, dobj);           //  업소정보변경
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_amt_change_DEL22(Conn, dobj);           //  사용료정보삭제
                if( dobj.getRetObject("R").getRecord().get("BSTYP_CD").equals("o"))
                {
                    dobj  = CALLupso_amt_change_DEL24(Conn, dobj);           //  삭제
                    dobj  = CALLupso_amt_change_SEL26(Conn, dobj);           //  기기대수정보
                    dobj  = CALLupso_amt_change_UPD25(Conn, dobj);           //  업소정보변경
                }
                else
                {
                    dobj  = CALLupso_amt_change_SEL26(Conn, dobj);           //  기기대수정보
                    dobj  = CALLupso_amt_change_UPD25(Conn, dobj);           //  업소정보변경
                }
            }
        }
        return dobj;
    }
    // 사용료정보삭제
    public DOBJ CALLupso_amt_change_DEL22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL22");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_DEL22(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL22") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_DEL22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CHG_DAY = dvobj.getRecord().get("CHG_DAY");   //변경 일자
        String   CHG_NUM = dvobj.getRecord().get("CHG_NUM");   //변경 번호
        String   CHG_BRAN = dvobj.getRecord().get("CHG_BRAN");   //변경 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" where CHG_BRAN=:CHG_BRAN  \n";
        query +=" and CHG_NUM=:CHG_NUM  \n";
        query +=" and CHG_DAY=:CHG_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        return sobj;
    }
    // 사용료수정
    // 사용료 정보를 수정한다.
    public DOBJ CALLupso_amt_change_UPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_UPD7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_UPD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CHG_NUM = dvobj.getRecord().get("CHG_NUM");   //변경 번호
        double   MONPRNCFEE2 = dvobj.getRecord().getDouble("MONPRNCFEE2");   //기준월정료
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //업종 코드
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //월정료
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //기계대수
        String   USE_TIME = dvobj.getRecord().get("USE_TIME");   //사용시간
        String   UPSO_GRAD = dvobj.getRecord().get("UPSO_GRAD");   //업소 등급
        String   CHG_DAY = dvobj.getRecord().get("CHG_DAY");   //변경 일자
        String   CHG_BRAN = dvobj.getRecord().get("CHG_BRAN");   //변경 지부
        String   APPL_DAY = dvobj.getRecord().get("APPL_DAY");   //적용 일자
        String   MODPRES_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , APPL_DAY=:APPL_DAY , UPSO_GRAD=:UPSO_GRAD , USE_TIME=:USE_TIME , MCHNDAESU=:MCHNDAESU , MONPRNCFEE=:MONPRNCFEE , BSTYP_CD=:BSTYP_CD , MONPRNCFEE2=:MONPRNCFEE2 , MOD_DATE=SYSDATE , REMAK=:REMAK  \n";
        query +=" where CHG_BRAN=:CHG_BRAN  \n";
        query +=" and CHG_DAY=:CHG_DAY  \n";
        query +=" and CHG_NUM=:CHG_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setDouble("MONPRNCFEE2", MONPRNCFEE2);               //기준월정료
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //월정료
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //기계대수
        sobj.setString("USE_TIME", USE_TIME);               //사용시간
        sobj.setString("UPSO_GRAD", UPSO_GRAD);               //업소 등급
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("APPL_DAY", APPL_DAY);               //적용 일자
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // CHG_NUM생성
    // 사용료의 일련번호(4자리)를 생성한다
    public DOBJ CALLupso_amt_change_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_amt_change_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("CHG_BRAN");   //변경 지부
        String   CHG_DAY = dobj.getRetObject("R").getRecord().get("CHG_DAY");   //변경 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(CHG_NUM),  0)  +  1,  4,  '0')  CHG_NUM  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  CHG_BRAN  =  :CHG_BRAN   \n";
        query +=" AND  CHG_DAY  =  :CHG_DAY   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 사용료변경
    // 사용료 정보를 등록한다.
    public DOBJ CALLupso_amt_change_INS6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_INS6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double JOIN_SEQ = 0;        //사용료인덱스
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        double   MONPRNCFEE2 = dvobj.getRecord().getDouble("MONPRNCFEE2");   //기준월정료
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //업종 코드
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //월정료
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //기계대수
        String   USE_TIME = dvobj.getRecord().get("USE_TIME");   //사용시간
        String   UPSO_GRAD = dvobj.getRecord().get("UPSO_GRAD");   //업소 등급
        String   CHG_DAY = dvobj.getRecord().get("CHG_DAY");   //변경 일자
        String   CHG_BRAN = dvobj.getRecord().get("CHG_BRAN");   //변경 지부
        String   APPL_DAY = dvobj.getRecord().get("APPL_DAY");   //적용 일자
        String   CHG_NUM = dobj.getRetObject("SEL7").getRecord().get("CHG_NUM");   //변경 번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, USE_TIME, MCHNDAESU, MONPRNCFEE, BSTYP_CD, MONPRNCFEE2, INS_DATE, CHG_NUM, UPSO_CD, REMAK)  \n";
        query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), :APPL_DAY , :CHG_BRAN , :CHG_DAY , :INSPRES_ID , :UPSO_GRAD , :USE_TIME , :MCHNDAESU , :MONPRNCFEE , :BSTYP_CD , :MONPRNCFEE2 , SYSDATE, :CHG_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("MONPRNCFEE2", MONPRNCFEE2);               //기준월정료
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //월정료
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //기계대수
        sobj.setString("USE_TIME", USE_TIME);               //사용시간
        sobj.setString("UPSO_GRAD", UPSO_GRAD);               //업소 등급
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("APPL_DAY", APPL_DAY);               //적용 일자
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 삭제
    // 기존 노래방 정보를 삭제한다.
    public DOBJ CALLupso_amt_change_DEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL24");
        VOBJ dvobj = dobj.getRetObject("S1");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_DEL24(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL24") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_DEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //업종 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CHG_DAY = dvobj.getRecord().get("CHG_DAY");   //변경 일자
        String   CHG_NUM = dvobj.getRecord().get("CHG_NUM");   //변경 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where CHG_NUM=:CHG_NUM  \n";
        query +=" and CHG_DAY=:CHG_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and BSTYP_CD=:BSTYP_CD";
        sobj.setSql(query);
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        return sobj;
    }
    // 업소정보변경
    // 업소의 기기대수 정보를 변경한다.
    public DOBJ CALLupso_amt_change_UPD42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD42");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_UPD42(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD42") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_UPD42(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //기계대수
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MCHNDAESU=:MCHNDAESU , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //기계대수
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 기기대수정보
    // 삭제후 가장 최근 등록된 사용료의 기기대수를 구한다.
    public DOBJ CALLupso_amt_change_SEL26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL26");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL26");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_amt_change_SEL26(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL26");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_SEL26(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MCHNDAESU  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  JOIN_SEQ  =  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소정보변경
    // 업소의 기기대수 정보를 변경한다.
    public DOBJ CALLupso_amt_change_UPD40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD40");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_UPD40(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD40") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_UPD40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //기계대수
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MCHNDAESU=:MCHNDAESU , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //기계대수
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 업소정보변경
    // 업소의 기기대수 정보를 변경한다.
    public DOBJ CALLupso_amt_change_UPD25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD25");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_UPD25(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD25") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_UPD25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        double   MCHNDAESU = dobj.getRetObject("SEL26").getRecord().getDouble("MCHNDAESU");   //기계대수
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MCHNDAESU=:MCHNDAESU , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //기계대수
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 노래방정보
    public DOBJ CALLupso_amt_change_MIUD29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD29");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_amt_change_SEL28(Conn, dobj);           //  기준금액 획득
                dobj  = CALLupso_amt_change_INS33(Conn, dobj);           //  신규
                dobj  = CALLupso_amt_change_XIUD30(Conn, dobj);           //  월정료수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_amt_change_SEL29(Conn, dobj);           //  기준금액 획득
                dobj  = CALLupso_amt_change_UPD35(Conn, dobj);           //  수정
                dobj  = CALLupso_amt_change_XIUD31(Conn, dobj);           //  월정료수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_amt_change_DEL34(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    // 기존 노래방 정보를 삭제한다.
    public DOBJ CALLupso_amt_change_DEL34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL34");
        VOBJ dvobj = dobj.getRetObject("S1");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_DEL34(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_DEL34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   GRAD_NUM = dvobj.getRecord().getDouble("GRAD_NUM");   //노래방순번
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CHG_DAY = dvobj.getRecord().get("CHG_DAY");   //변경 일자
        String   CHG_NUM = dvobj.getRecord().get("CHG_NUM");   //변경 번호
        String   CHG_BRAN = dvobj.getRecord().get("CHG_BRAN");   //변경 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where CHG_BRAN=:CHG_BRAN  \n";
        query +=" and CHG_NUM=:CHG_NUM  \n";
        query +=" and CHG_DAY=:CHG_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and GRAD_NUM=:GRAD_NUM";
        sobj.setSql(query);
        sobj.setDouble("GRAD_NUM", GRAD_NUM);               //노래방순번
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        return sobj;
    }
    // 기준금액 획득
    public DOBJ CALLupso_amt_change_SEL29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL29");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL29");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_amt_change_SEL29(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL29");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_SEL29(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("CHG_BRAN");   //변경 지부
        String   CHG_NUM = dobj.getRetObject("R").getRecord().get("CHG_NUM");   //변경 번호
        String   CHG_DAY = dobj.getRetObject("R").getRecord().get("CHG_DAY");   //변경 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   BSTYP_CD = dobj.getRetObject("R").getRecord().get("BSTYP_CD");   //업종 코드
        String   GRAD_GBN = dobj.getRetObject("R").getRecord().get("GRAD_GBN");   //등급 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CASE  WHEN  APPL_DAY  <  '20130601'  THEN   \n";
        query +=" (SELECT  STNDAMT  FROM  GIBU.TBRA_BSTYPGRAD_TEMP2013  WHERE  BSTYP_CD  =  :BSTYP_CD   \n";
        query +=" AND  GRAD_GBN  =  :GRAD_GBN)  ELSE   \n";
        query +=" (SELECT  STNDAMT  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  :BSTYP_CD   \n";
        query +=" AND  GRAD_GBN  =  :GRAD_GBN)END  STNDAMT  ,   \n";
        query +=" (SELECT  /*+  INDEX_DESC(TBRA_FEERATE_HISTY  TBRA_FEERATE_HISTY_IDX_PK)  */  RATE  FROM  GIBU.TBRA_FEERATE_HISTY  WHERE  APPL_DAY  <=  A.APPL_DAY   \n";
        query +=" AND  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(:UPSO_CD)   \n";
        query +=" AND  ROWNUM  =  1)  RATE  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  CHG_DAY  =  :CHG_DAY   \n";
        query +=" AND  CHG_NUM  =  :CHG_NUM   \n";
        query +=" AND  CHG_BRAN  =  :CHG_BRAN ";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("GRAD_GBN", GRAD_GBN);               //등급 구분
        return sobj;
    }
    // 기준금액 획득
    public DOBJ CALLupso_amt_change_SEL28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL28");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL28");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_amt_change_SEL28(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL28");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_SEL28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("CHG_BRAN");   //변경 지부
        String  CHG_NUM="";          //변경 번호
        if( ( dobj.getRetObject("R").getRecord().get("CHG_NUM").equals("") ))
        {
            CHG_NUM = dobj.getRetObject("SEL7").getRecord().get("CHG_NUM");
        }
        else
        {
            CHG_NUM = dobj.getRetObject("R").getRecord().get("CHG_NUM");
        }
        String   CHG_DAY = dobj.getRetObject("R").getRecord().get("CHG_DAY");   //변경 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   BSTYP_CD = dobj.getRetObject("R").getRecord().get("BSTYP_CD");   //업종 코드
        String   GRAD_GBN = dobj.getRetObject("R").getRecord().get("GRAD_GBN");   //등급 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CASE  WHEN  APPL_DAY  <  '20130601'  THEN   \n";
        query +=" (SELECT  STNDAMT  FROM  GIBU.TBRA_BSTYPGRAD_TEMP2013  WHERE  BSTYP_CD  =  :BSTYP_CD   \n";
        query +=" AND  GRAD_GBN  =  :GRAD_GBN)  ELSE   \n";
        query +=" (SELECT  STNDAMT  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  :BSTYP_CD   \n";
        query +=" AND  GRAD_GBN  =  :GRAD_GBN)END  STNDAMT  ,   \n";
        query +=" (SELECT  /*+  INDEX_DESC(TBRA_FEERATE_HISTY  TBRA_FEERATE_HISTY_IDX_PK)  */  RATE  FROM  GIBU.TBRA_FEERATE_HISTY  WHERE  APPL_DAY  <=  A.APPL_DAY   \n";
        query +=" AND  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(:UPSO_CD)   \n";
        query +=" AND  ROWNUM  =  1)  RATE  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  CHG_DAY  =  :CHG_DAY   \n";
        query +=" AND  CHG_NUM  =  :CHG_NUM   \n";
        query +=" AND  CHG_BRAN  =  :CHG_BRAN ";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("GRAD_GBN", GRAD_GBN);               //등급 구분
        return sobj;
    }
    // 신규
    // 노래방 정보를 신규등록한다.
    public DOBJ CALLupso_amt_change_INS33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS33");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS33");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_INS33(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS33") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_INS33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double GRAD_NUM = 0;        //노래방순번
        String INS_DATE ="";        //등록 일시
        String   GRAD_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //노래방순번
        String   GRAD_GBN = dvobj.getRecord().get("GRAD_GBN");   //등급 구분
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //업종 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        double   AREA = dvobj.getRecord().getDouble("AREA");   //면적
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //기계대수
        String   CHG_DAY = dvobj.getRecord().get("CHG_DAY");   //변경 일자
        String   CHG_BRAN = dvobj.getRecord().get("CHG_BRAN");   //변경 지부
        String  CHG_NUM="";          //변경 번호
        if( ( dobj.getRetObject("R").getRecord().get("CHG_NUM").equals("") ))
        {
            CHG_NUM = dobj.getRetObject("SEL7").getRecord().get("CHG_NUM");
        }
        else
        {
            CHG_NUM = dobj.getRetObject("R").getRecord().get("CHG_NUM");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        double   STNDAMT = dobj.getRetObject("SEL28").getRecord().getDouble("STNDAMT");   //기준금액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, STNDAMT, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
        query +=" values(SYSDATE, :CHG_BRAN , :INSPRES_ID , :CHG_NUM , :CHG_DAY , :STNDAMT , :MCHNDAESU , :AREA , :UPSO_CD , :BSTYP_CD , :GRAD_GBN , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = :GRAD_NUM_1))";
        sobj.setSql(query);
        sobj.setString("GRAD_NUM_1", GRAD_NUM_1);               //노래방순번
        sobj.setString("GRAD_GBN", GRAD_GBN);               //등급 구분
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("AREA", AREA);               //면적
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //기계대수
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setDouble("STNDAMT", STNDAMT);               //기준금액
        return sobj;
    }
    // 수정
    // 기존 노래방 정보를 수정한다.
    public DOBJ CALLupso_amt_change_UPD35(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD35");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("UPD35");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_UPD35(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD35") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_UPD35(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        double   GRAD_NUM = dvobj.getRecord().getDouble("GRAD_NUM");   //노래방순번
        String   GRAD_GBN = dvobj.getRecord().get("GRAD_GBN");   //등급 구분
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //업종 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        double   AREA = dvobj.getRecord().getDouble("AREA");   //면적
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //기계대수
        String   CHG_DAY = dvobj.getRecord().get("CHG_DAY");   //변경 일자
        String   CHG_NUM = dvobj.getRecord().get("CHG_NUM");   //변경 번호
        String   CHG_BRAN = dvobj.getRecord().get("CHG_BRAN");   //변경 지부
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        double   STNDAMT = dobj.getRetObject("SEL29").getRecord().getDouble("STNDAMT");   //기준금액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , STNDAMT=:STNDAMT , MCHNDAESU=:MCHNDAESU , AREA=:AREA , MOD_DATE=SYSDATE , BSTYP_CD=:BSTYP_CD , GRAD_GBN=:GRAD_GBN  \n";
        query +=" where CHG_BRAN=:CHG_BRAN  \n";
        query +=" and CHG_NUM=:CHG_NUM  \n";
        query +=" and CHG_DAY=:CHG_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and GRAD_NUM=:GRAD_NUM";
        sobj.setSql(query);
        sobj.setDouble("GRAD_NUM", GRAD_NUM);               //노래방순번
        sobj.setString("GRAD_GBN", GRAD_GBN);               //등급 구분
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("AREA", AREA);               //면적
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //기계대수
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setDouble("STNDAMT", STNDAMT);               //기준금액
        return sobj;
    }
    // 월정료수정
    public DOBJ CALLupso_amt_change_XIUD30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD30");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_XIUD30(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD30");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_XIUD30(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("CHG_BRAN");   //변경 지부
        String   CHG_DAY = dobj.getRetObject("R").getRecord().get("CHG_DAY");   //변경 일자
        String  CHG_NUM="";          //변경 번호
        if( ( dobj.getRetObject("R").getRecord().get("CHG_NUM").equals("") ))
        {
            CHG_NUM = dobj.getRetObject("SEL7").getRecord().get("CHG_NUM");
        }
        else
        {
            CHG_NUM = dobj.getRetObject("R").getRecord().get("CHG_NUM");
        }
        double   RATE = dobj.getRetObject("SEL28").getRecord().getDouble("RATE");   //요율
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" SET MONPRNCFEE2 = (SELECT SUM(STNDAMT * NVL(MCHNDAESU,0)) FROM  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND CHG_DAY = :CHG_DAY  \n";
        query +=" AND CHG_NUM = :CHG_NUM  \n";
        query +=" AND CHG_BRAN = :CHG_BRAN) , MONPRNCFEE = (SELECT TRUNC(SUM(STNDAMT * NVL(MCHNDAESU,0)) * 0.01 * :RATE, -1) FROM  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND CHG_DAY = :CHG_DAY  \n";
        query +=" AND CHG_NUM = :CHG_NUM  \n";
        query +=" AND CHG_BRAN = :CHG_BRAN)  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND CHG_DAY = :CHG_DAY  \n";
        query +=" AND CHG_NUM = :CHG_NUM  \n";
        query +=" AND CHG_BRAN = :CHG_BRAN";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setDouble("RATE", RATE);               //요율
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 월정료수정
    public DOBJ CALLupso_amt_change_XIUD31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD31");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_amt_change_XIUD31(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_XIUD31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("CHG_BRAN");   //변경 지부
        String   CHG_DAY = dobj.getRetObject("R").getRecord().get("CHG_DAY");   //변경 일자
        String   CHG_NUM = dobj.getRetObject("R").getRecord().get("CHG_NUM");   //변경 번호
        double   RATE = dobj.getRetObject("SEL29").getRecord().getDouble("RATE");   //요율
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" SET MONPRNCFEE2 = (SELECT SUM(STNDAMT * NVL(MCHNDAESU,0)) FROM  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND CHG_DAY = :CHG_DAY  \n";
        query +=" AND CHG_NUM = :CHG_NUM  \n";
        query +=" AND CHG_BRAN = :CHG_BRAN) , MONPRNCFEE = (SELECT TRUNC(SUM(STNDAMT * NVL(MCHNDAESU,0)) * 0.01 * :RATE,-1) FROM  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND CHG_DAY = :CHG_DAY  \n";
        query +=" AND CHG_NUM = :CHG_NUM  \n";
        query +=" AND CHG_BRAN = :CHG_BRAN)  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND CHG_DAY = :CHG_DAY  \n";
        query +=" AND CHG_NUM = :CHG_NUM  \n";
        query +=" AND CHG_BRAN = :CHG_BRAN";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setDouble("RATE", RATE);               //요율
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소사용료리스트
    // 해당 업소의 사용료 히스토리를 조회한다.
    public DOBJ CALLupso_amt_change_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_amt_change_SEL9(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROWNUM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  ,  TRIM(A.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  TRIM(A.BSTYP_CD)  BSTYP_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.MONPRNCFEE2  ,  A.APPL_DAY  ,  A.MCHNDAESU  ,  A.REMAK  ,  A.UPSO_CD  ,  A.USE_TIME  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =:UPSO_CD  ORDER  BY  A.CHG_DAY,  A.CHG_NUM ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 노래방상세정보
    // 첫번째 정보의 업종코드가 노래방일 경우 노래방 상세정보도 같이 보내준다
    public DOBJ CALLupso_amt_change_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_amt_change_SEL19(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_change_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("SEL9").getRecord().get("CHG_BRAN");   //변경 지부
        String   CHG_NUM = dobj.getRetObject("SEL9").getRecord().get("CHG_NUM");   //변경 번호
        String   CHG_DAY = dobj.getRetObject("SEL9").getRecord().get("CHG_DAY");   //변경 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  TRIM(A.BSTYP_CD)  BSTYP_CD  ,  A.GRAD_GBN  ,  A.AREA  ,  A.MCHNDAESU  ,  A.STNDAMT  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  ,  A.GRAD_NUM  ,  (A.MCHNDAESU  *  A.STNDAMT)  AMT  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN   \n";
        query +=" AND  A.CHG_DAY  =  :CHG_DAY   \n";
        query +=" AND  A.CHG_NUM  =  :CHG_NUM   \n";
        query +=" AND  A.CHG_BRAN  =  :CHG_BRAN ";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upso_amt_change
    //##**$$upso_noraebang_select
    /* * 프로그램명 : bra01_s03
    * 작성자 : 서정재
    * 작성일 : 2009/09/28
    * 설명 : 해당 업소의 노래방 상세정보를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_noraebang_select(DOBJ dobj)
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
            dobj  = CALLupso_noraebang_select_SEL1(Conn, dobj);           //  노래방상세정보
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
    public DOBJ CTLupso_noraebang_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_noraebang_select_SEL1(Conn, dobj);           //  노래방상세정보
        return dobj;
    }
    // 노래방상세정보
    // 해당 업소의 노래방 상세정보를 조회한다.
    public DOBJ CALLupso_noraebang_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_noraebang_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_noraebang_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("S").firstRecord().get("CHG_BRAN");   //변경 지부
        String   CHG_NUM = dobj.getRetObject("S").firstRecord().get("CHG_NUM");   //변경 번호
        String   CHG_DAY = dobj.getRetObject("S").firstRecord().get("CHG_DAY");   //변경 일자
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  TRIM(A.BSTYP_CD  )  BSTYP_CD  ,  A.GRAD_GBN  ,  A.AREA  ,  A.MCHNDAESU  ,  A.STNDAMT  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  ,  A.MCHNDAESU  *  A.STNDAMT  AMT  ,  A.GRAD_NUM  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN   \n";
        query +=" AND  A.CHG_DAY  =  :CHG_DAY   \n";
        query +=" AND  A.CHG_NUM  =  :CHG_NUM   \n";
        query +=" AND  A.CHG_BRAN  =  :CHG_BRAN ";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upso_noraebang_select
    //##**$$feerate_select
    /*
    */
    public DOBJ CTLfeerate_select(DOBJ dobj)
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
            dobj  = CALLfeerate_select_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLfeerate_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLfeerate_select_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLfeerate_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLfeerate_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLfeerate_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_DAY = dobj.getRetObject("S").getRecord().get("APPL_DAY");   //적용 일자
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  INDEX_DESC(TBRA_FEERATE_HISTY  TBRA_FEERATE_HISTY_IDX_PK)  */  RATE  FROM  GIBU.TBRA_FEERATE_HISTY  WHERE  APPL_DAY  <=  :APPL_DAY   \n";
        query +=" AND  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(:UPSO_CD)   \n";
        query +=" AND  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("APPL_DAY", APPL_DAY);               //적용 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$feerate_select
    //##**$$end
}
