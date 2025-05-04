
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s02
{
    public bra01_s02()
    {
    }
    //##**$$upso_acmcn_regist
    /* * 프로그램명 : bra01_s02
    * 작성자 : 서정재
    * 작성일 : 2009/10/21
    * 설명 : 업소의 반주기 정보를 등록한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_acmcn_regist(DOBJ dobj)
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
            dobj  = CALLupso_acmcn_regist_DEL22(Conn, dobj);           //  온오프반주기정보삭제
            dobj  = CALLupso_acmcn_regist_MPD24(Conn, dobj);           //  반주기온오프정보
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_acmcn_regist_SEL19(Conn, dobj);           //  온오프정보조회
            if( dobj.getRetObject("SEL19").getRecordCnt() == 1)
            {
                dobj  = CALLupso_acmcn_regist_UPD21(Conn, dobj);           //  업소 온오프정보수정
                dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  반주기모델정보삭제
                dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  반주기모델정보
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  오브리정보
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else if( dobj.getRetObject("SEL19").getRecordCnt() > 1)
            {
                dobj  = CALLupso_acmcn_regist_UPD22(Conn, dobj);           //  업소 온오프정보수정
                dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  반주기모델정보삭제
                dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  반주기모델정보
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  오브리정보
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else
            {
                dobj  = CALLupso_acmcn_regist_UPD25(Conn, dobj);           //  업소 온오프정보수정
                dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  반주기모델정보삭제
                dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  반주기모델정보
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  오브리정보
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
    public DOBJ CTLupso_acmcn_regist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_acmcn_regist_DEL22(Conn, dobj);           //  온오프반주기정보삭제
        dobj  = CALLupso_acmcn_regist_MPD24(Conn, dobj);           //  반주기온오프정보
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_acmcn_regist_SEL19(Conn, dobj);           //  온오프정보조회
        if( dobj.getRetObject("SEL19").getRecordCnt() == 1)
        {
            dobj  = CALLupso_acmcn_regist_UPD21(Conn, dobj);           //  업소 온오프정보수정
            dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  반주기모델정보삭제
            dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  반주기모델정보
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  오브리정보
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else if( dobj.getRetObject("SEL19").getRecordCnt() > 1)
        {
            dobj  = CALLupso_acmcn_regist_UPD22(Conn, dobj);           //  업소 온오프정보수정
            dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  반주기모델정보삭제
            dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  반주기모델정보
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  오브리정보
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else
        {
            dobj  = CALLupso_acmcn_regist_UPD25(Conn, dobj);           //  업소 온오프정보수정
            dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  반주기모델정보삭제
            dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  반주기모델정보
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  오브리정보
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // 온오프반주기정보삭제
    // 해당 업소의 기존 온오프 반주기 정보를 삭제한다.
    public DOBJ CALLupso_acmcn_regist_DEL22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL22");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_DEL22(dobj, dvobj);
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
    private SQLObject SQLupso_acmcn_regist_DEL22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_ONOFF_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 반주기온오프정보
    public DOBJ CALLupso_acmcn_regist_MPD24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD24");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MPD24");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("UPSO_CD").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_acmcn_regist_INS16(Conn, dobj);           //  온오프반주기정보등록
            }
        }
        return dobj;
    }
    // 온오프반주기정보등록
    // 새로운 온오프 반주기 정보를 등록한다.
    public DOBJ CALLupso_acmcn_regist_INS16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_INS16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_INS16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //등록자 ID
        String INS_DATE ="";        //등록 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   ONOFF_GBN = dvobj.getRecord().get("ONOFF_GBN");   //온오프 구분
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //반주기 대수
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_ONOFF_INFO (MODEL_CD, ACMCN_DAESU, ONOFF_GBN, UPSO_CD)  \n";
        query +=" values(:MODEL_CD , :ACMCN_DAESU , :ONOFF_GBN , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //온오프 구분
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //반주기 대수
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        return sobj;
    }
    // 온오프정보조회
    // 해당 업소의 온오프 정보를 조회한다.
    public DOBJ CALLupso_acmcn_regist_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_acmcn_regist_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  ONOFF_GBN  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소 온오프정보수정
    // 온오프 정보가 1개일 경우 해당 온오프 정보로 수정한다.
    public DOBJ CALLupso_acmcn_regist_UPD21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD21");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_UPD21(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD21") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_UPD21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //수정자 ID
        String   ONOFF_GBN = dobj.getRetObject("SEL19").getRecord().get("ONOFF_GBN");   //온오프 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //온오프 구분
        return sobj;
    }
    // 반주기모델정보삭제
    // 기존 반주기 모델정보를 삭제한다.
    public DOBJ CALLupso_acmcn_regist_DEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL23");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_DEL23(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL23") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_DEL23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_ACMCN_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 반주기모델정보
    public DOBJ CALLupso_acmcn_regist_MPD26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD26");
        VOBJ dvobj = dobj.getRetObject("S1");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("UPSO_CD").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_acmcn_regist_INS17(Conn, dobj);           //  반주기모델정보등록
            }
        }
        return dobj;
    }
    // 반주기모델정보등록
    // 새로 등록된 반주기 모델 정보를 등록한다.
    public DOBJ CALLupso_acmcn_regist_INS17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS17");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_INS17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS17") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_INS17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //등록자 ID
        String INS_DATE ="";        //등록 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //반주기 대수
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_ACMCN_INFO (MODEL_CD, ACMCN_DAESU, UPSO_CD)  \n";
        query +=" values(:MODEL_CD , :ACMCN_DAESU , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //반주기 대수
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        return sobj;
    }
    // 오브리정보
    public DOBJ CALLupso_acmcn_regist_MIUD30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD30");
        VOBJ dvobj = dobj.getRetObject("S2");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_acmcn_regist_INS18(Conn, dobj);           //  등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_acmcn_regist_UPD19(Conn, dobj);           //  수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_acmcn_regist_DEL20(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    // 기존 오브리 정보를 삭제한다.
    public DOBJ CALLupso_acmcn_regist_DEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL20");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_DEL20(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL20") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_DEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_AUBRY_INFO  \n";
        query +=" where MODEL_CD=:MODEL_CD  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        return sobj;
    }
    // 등록
    // 새로운 오브리 정보를 등록한다.
    public DOBJ CALLupso_acmcn_regist_INS18(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupso_acmcn_regist_INS18(dobj, dvobj);
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
    private SQLObject SQLupso_acmcn_regist_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //등록자 ID
        String INS_DATE ="";        //등록 일시
        String   MODEL_NM = dvobj.getRecord().get("MODEL_NM");   //모델 명
        String   MCHN_COMPY = dvobj.getRecord().get("MCHN_COMPY");   //기계 회사
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MCHN_COMPYNM = dvobj.getRecord().get("MCHN_COMPYNM");   //기계 회사명
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUBRY_INFO (MODEL_CD, MCHN_COMPYNM, UPSO_CD, MCHN_COMPY, MODEL_NM)  \n";
        query +=" values(:MODEL_CD , :MCHN_COMPYNM , :UPSO_CD , :MCHN_COMPY , :MODEL_NM )";
        sobj.setSql(query);
        sobj.setString("MODEL_NM", MODEL_NM);               //모델 명
        sobj.setString("MCHN_COMPY", MCHN_COMPY);               //기계 회사
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MCHN_COMPYNM", MCHN_COMPYNM);               //기계 회사명
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        return sobj;
    }
    // 수정
    // 기존 오브리 정보를 수정한다.
    public DOBJ CALLupso_acmcn_regist_UPD19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD19");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_UPD19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD19") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_UPD19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //수정자 ID
        String MOD_DATE ="";        //수정 일시
        String   MODEL_NM = dvobj.getRecord().get("MODEL_NM");   //모델 명
        String   MCHN_COMPY = dvobj.getRecord().get("MCHN_COMPY");   //기계 회사
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MCHN_COMPYNM = dvobj.getRecord().get("MCHN_COMPYNM");   //기계 회사명
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUBRY_INFO  \n";
        query +=" set MCHN_COMPYNM=:MCHN_COMPYNM , MCHN_COMPY=:MCHN_COMPY , MODEL_NM=:MODEL_NM  \n";
        query +=" where MODEL_CD=:MODEL_CD  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODEL_NM", MODEL_NM);               //모델 명
        sobj.setString("MCHN_COMPY", MCHN_COMPY);               //기계 회사
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MCHN_COMPYNM", MCHN_COMPYNM);               //기계 회사명
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        return sobj;
    }
    // 업소 온오프정보수정
    // ON/OFF둘다 있을 경우 업소의 온오프정보는 온라인(O)이 된다.
    public DOBJ CALLupso_acmcn_regist_UPD22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD22");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_UPD22(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD22") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_UPD22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //수정자 ID
        String   ONOFF_GBN ="O";   //온오프 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //온오프 구분
        return sobj;
    }
    // 업소 온오프정보수정
    // 온오프정보가 없을 경우 기존 ONOFF_GBN을 ''로 UPDATE
    public DOBJ CALLupso_acmcn_regist_UPD25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD25");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_UPD25(dobj, dvobj);
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
    private SQLObject SQLupso_acmcn_regist_UPD25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //수정자 ID
        String   ONOFF_GBN ="";   //온오프 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //온오프 구분
        return sobj;
    }
    //##**$$upso_acmcn_regist
    //##**$$upso_acmcn_select
    /* * 프로그램명 : bra01_s02
    * 작성자 : 박태현
    * 작성일 : 2009/10/15
    * 설명 : 업소별 반주
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_acmcn_select(DOBJ dobj)
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
            dobj  = CALLupso_acmcn_select_SEL1(Conn, dobj);           //  업소별 온오프 반주기조회
            dobj  = CALLupso_acmcn_select_SEL2(Conn, dobj);           //  업소별 반주기조회
            dobj  = CALLupso_acmcn_select_SEL3(Conn, dobj);           //  업소별 오브리 정보 보회
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
    public DOBJ CTLupso_acmcn_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_acmcn_select_SEL1(Conn, dobj);           //  업소별 온오프 반주기조회
        dobj  = CALLupso_acmcn_select_SEL2(Conn, dobj);           //  업소별 반주기조회
        dobj  = CALLupso_acmcn_select_SEL3(Conn, dobj);           //  업소별 오브리 정보 보회
        return dobj;
    }
    // 업소별 온오프 반주기조회
    public DOBJ CALLupso_acmcn_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_acmcn_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XB.KIND  MCHN_COMPY  ,  NVL(SUM(XA.ACMCN_DAESU),  0)  ACMCN_DAESU  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  (CASE  B.MCHN_COMPY  WHEN  'E0006'  THEN  B.MCHN_COMPY  WHEN  'E0003'  THEN  B.MCHN_COMPY  ELSE  'ETC'  END)  MCHN_COMPY  ,  A.ONOFF_GBN  ,  ACMCN_DAESU  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A  ,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD  )  XA  ,  (   \n";
        query +=" SELECT  '1_KY_O'  KIND,  'E0006'  MCHN_COMPY,  'O'  ONOFF_GBN  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '4_KY_F',  'E0006',  'F'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '2_TJ_O',  'E0003',  'O'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '5_TJ_F',  'E0003',  'F'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '3_ETC_O',  'ETC'  ,  'O'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '6_ETC_F',  'ETC'  ,  'F'  FROM  DUAL  )  XB  WHERE  XB.ONOFF_GBN  =  XA.ONOFF_GBN  (+)   \n";
        query +=" AND  XB.MCHN_COMPY  =  XA.MCHN_COMPY  (+)  GROUP  BY  XB.KIND,  XA.UPSO_CD  ORDER  BY  XB.KIND ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소별 반주기조회
    public DOBJ CALLupso_acmcn_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_acmcn_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  KMCHN_COMPY  ,  XA.MODEL_CD  KMODEL_CD  ,  XA.MODEL_NM  KMODEL_NM  ,  XA.ACMCN_DAESU  KACMCN_DAESU  ,  XA.GBN  KGBN  ,  XB.MCHN_COMPY  TMCHN_COMPY  ,  XB.MODEL_CD  TMODEL_CD  ,  XB.MODEL_NM  TMODEL_NM  ,  XB.ACMCN_DAESU  TACMCN_DAESU  ,  XB.GBN  TGBN  ,  XC.MCHN_COMPY  EMCHN_COMPY  ,  XC.MODEL_CD  EMODEL_CD  ,  XC.MODEL_NM  EMODEL_NM  ,  XC.ACMCN_DAESU  EACMCN_DAESU  ,  XC.GBN  EGBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XA.N  =  XB.N  (+)   \n";
        query +=" AND  XA.N  =  XC.N  (+)  UNION   \n";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  ,  XA.MODEL_CD  ,  XA.MODEL_NM  ,  XA.ACMCN_DAESU  ,  XA.GBN  ,  XB.MCHN_COMPY  ,  XB.MODEL_CD  ,  XB.MODEL_NM  ,  XB.ACMCN_DAESU  ,  XB.GBN  ,  XC.MCHN_COMPY  ,  XC.MODEL_CD  ,  XC.MODEL_NM  ,  XC.ACMCN_DAESU  ,  XC.GBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XB.N  =  XA.N  (+)   \n";
        query +=" AND  XB.N  =  XC.N  (+)  UNION   \n";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  ,  XA.MODEL_CD  ,  XA.MODEL_NM  ,  XA.ACMCN_DAESU  ,  XA.GBN  ,  XB.MCHN_COMPY  ,  XB.MODEL_CD  ,  XB.MODEL_NM  ,  XB.ACMCN_DAESU  ,  XB.GBN  ,  XC.MCHN_COMPY  ,  XC.MODEL_CD  ,  XC.MODEL_NM  ,  XC.ACMCN_DAESU  ,  XC.GBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XC.N  =  XA.N  (+)   \n";
        query +=" AND  XC.N  =  XB.N  (+) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소별 오브리 정보 보회
    public DOBJ CALLupso_acmcn_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_acmcn_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  MODEL_CD  ,  MODEL_NM  ,  MCHN_COMPYNM  ,  MCHN_COMPY  FROM  GIBU.TBRA_UPSO_AUBRY_INFO  WHERE  UPSO_CD=:UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upso_acmcn_select
    //##**$$acmcn_select
    /* * 프로그램명 : bra01_s02
    * 작성자 : 서정재
    * 작성일 : 2009/09/02
    * 설명 :
    반주기별 모델정보를 조회한다.
    MCHN_COMPY : ETC -> 0003, 0006을 제외한 반주기 정보 조회
    MCHN_COMPY : 0003 OR  0006-> 해당 코드 반주기 정보 조회
    MCHN_COMPY : 등록된 모든 반주기 정보 조회
    1.NLL.NLL3 : 프로세스빌더에서는 S 에서 바로 분기(조건달기)가 가능하지 않기 때문에 조건에 따라 분기가능하도록 넣음
    4.MRG.MRG1 : 각각 조건별로 조회된 데이타중 화면에 보여질 최종데이타를 MIFLATFORM에 전달한다
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLacmcn_select(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("ETC"))
            {
                dobj  = CALLacmcn_select_SEL2(Conn, dobj);           //  반주기정보조회(etc)
                dobj  = CALLacmcn_select_MRG1( dobj);        //  정보합침
            }
            else if( dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0003") || dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0006"))
            {
                dobj  = CALLacmcn_select_SEL1(Conn, dobj);           //  반주기정보조회
                dobj  = CALLacmcn_select_MRG1( dobj);        //  정보합침
            }
            else
            {
                dobj  = CALLacmcn_select_SEL3(Conn, dobj);           //  모든모델검색
                dobj  = CALLacmcn_select_MRG1( dobj);        //  정보합침
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
    public DOBJ CTLacmcn_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("ETC"))
        {
            dobj  = CALLacmcn_select_SEL2(Conn, dobj);           //  반주기정보조회(etc)
            dobj  = CALLacmcn_select_MRG1( dobj);        //  정보합침
        }
        else if( dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0003") || dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0006"))
        {
            dobj  = CALLacmcn_select_SEL1(Conn, dobj);           //  반주기정보조회
            dobj  = CALLacmcn_select_MRG1( dobj);        //  정보합침
        }
        else
        {
            dobj  = CALLacmcn_select_SEL3(Conn, dobj);           //  모든모델검색
            dobj  = CALLacmcn_select_MRG1( dobj);        //  정보합침
        }
        return dobj;
    }
    // 반주기정보조회(etc)
    public DOBJ CALLacmcn_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLacmcn_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLacmcn_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BSCONHAN_NM  MCHN_COMPYNM  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  FIDU.TLEV_BSCON  B  WHERE  MCHN_COMPY  NOT  IN  ('E0003',  'E0006')   \n";
        query +=" AND  A.MCHN_COMPY  =  B.BSCON_CD  ORDER  BY  A.MODEL_NM ";
        sobj.setSql(query);
        return sobj;
    }
    // 정보합침
    public DOBJ CALLacmcn_select_MRG1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL3, SEL1, SEL2","MCHN_COMPYNM, MCHN_COMPY, MODEL_CD, MODEL_NM, GBN");
        rvobj.setName("MRG1") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 반주기정보조회
    public DOBJ CALLacmcn_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLacmcn_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLacmcn_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MCHN_COMPY = dobj.getRetObject("S").getRecord().get("MCHN_COMPY");   //기계 회사
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BSCONHAN_NM  MCHN_COMPYNM  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  FIDU.TLEV_BSCON  B  WHERE  MCHN_COMPY  =  :MCHN_COMPY   \n";
        query +=" AND  A.MCHN_COMPY  =  B.BSCON_CD  ORDER  BY  A.MODEL_NM ";
        sobj.setSql(query);
        sobj.setString("MCHN_COMPY", MCHN_COMPY);               //기계 회사
        return sobj;
    }
    // 모든모델검색
    public DOBJ CALLacmcn_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLacmcn_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLacmcn_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BSCONHAN_NM  MCHN_COMPYNM  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  FIDU.TLEV_BSCON  B  WHERE  A.MCHN_COMPY  =  B.BSCON_CD  ORDER  BY  A.MODEL_NM ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$acmcn_select
    //##**$$end
}
