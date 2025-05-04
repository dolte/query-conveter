
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s03_1
{
    public bra10_s03_1()
    {
    }
    //##**$$save_juso
    /*
    */
    public DOBJ CTLsave_juso(DOBJ dobj)
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
            dobj  = CALLsave_juso_MIUD4(Conn, dobj);           //  수정된 행만 저장하도록
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
    public DOBJ CTLsave_juso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_juso_MIUD4(Conn, dobj);           //  수정된 행만 저장하도록
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 수정된 행만 저장하도록
    public DOBJ CALLsave_juso_MIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD4");
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
                dobj  = CALLsave_juso_SEL11(Conn, dobj);           //  더미
                if( dobj.getRetObject("R").getRecord().get("GBN2").equals("영업장"))
                {
                    dobj  = CALLsave_juso_XIUD2(Conn, dobj);           //  업소주소 저장
                }
                else if( dobj.getRetObject("R").getRecord().get("GBN2").equals("경영주"))
                {
                    dobj  = CALLsave_juso_XIUD19(Conn, dobj);           //  경영주주소저장
                }
                else if( dobj.getRetObject("R").getRecord().get("GBN2").equals("허가주"))
                {
                    dobj  = CALLsave_juso_XIUD20(Conn, dobj);           //  허가주주소저장
                }
            }
        }
        return dobj;
    }
    // 더미
    public DOBJ CALLsave_juso_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsave_juso_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_juso_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SYSDATE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 업소주소 저장
    public DOBJ CALLsave_juso_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_juso_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_juso_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BD_MNG_NUM = dobj.getRetObject("R").getRecord().get("BD_MNG_NUM");   //건물 관리 번호
        String   NEW_ADDR1 = dobj.getRetObject("R").getRecord().get("NEW_ADDR1");   //도로명주소1
        String   NEW_ADDR2 = dobj.getRetObject("R").getRecord().get("NEW_ADDR2");   //도로명주소2
        String   NEW_ZIP = dobj.getRetObject("R").getRecord().get("NEW_ZIP");   //우편번호
        String   NEW_ZIP1 = dobj.getRetObject("R").getRecord().get("NEW_ZIP1");   //우편번호
        String   REF_INFO = dobj.getRetObject("R").getRecord().get("REF_INFO");   //참고 항목
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET UPSO_NEW_ZIP = :NEW_ZIP , UPSO_NEW_ZIP1 = :NEW_ZIP1 , UPSO_NEW_ADDR1 = :NEW_ADDR1 , UPSO_NEW_ADDR2 = :NEW_ADDR2 , UPSO_REF_INFO = :REF_INFO , UPSO_BD_MNG_NUM = :BD_MNG_NUM  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BD_MNG_NUM", BD_MNG_NUM);               //건물 관리 번호
        sobj.setString("NEW_ADDR1", NEW_ADDR1);               //도로명주소1
        sobj.setString("NEW_ADDR2", NEW_ADDR2);               //도로명주소2
        sobj.setString("NEW_ZIP", NEW_ZIP);               //우편번호
        sobj.setString("NEW_ZIP1", NEW_ZIP1);               //우편번호
        sobj.setString("REF_INFO", REF_INFO);               //참고 항목
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 경영주주소저장
    public DOBJ CALLsave_juso_XIUD19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD19");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_juso_XIUD19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_juso_XIUD19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BD_MNG_NUM = dobj.getRetObject("R").getRecord().get("BD_MNG_NUM");   //건물 관리 번호
        String   NEW_ADDR1 = dobj.getRetObject("R").getRecord().get("NEW_ADDR1");   //도로명주소1
        String   NEW_ADDR2 = dobj.getRetObject("R").getRecord().get("NEW_ADDR2");   //도로명주소2
        String   NEW_ZIP = dobj.getRetObject("R").getRecord().get("NEW_ZIP");   //우편번호
        String   NEW_ZIP1 = dobj.getRetObject("R").getRecord().get("NEW_ZIP1");   //우편번호
        String   REF_INFO = dobj.getRetObject("R").getRecord().get("REF_INFO");   //참고 항목
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET MNGEMSTR_NEW_ZIP = :NEW_ZIP , MNGEMSTR_NEW_ZIP1 = :NEW_ZIP1 , MNGEMSTR_NEW_ADDR1 = :NEW_ADDR1 , MNGEMSTR_NEW_ADDR2 = :NEW_ADDR2 , MNGEMSTR_REF_INFO = :REF_INFO , MNGEMSTR_BD_MNG_NUM = :BD_MNG_NUM  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BD_MNG_NUM", BD_MNG_NUM);               //건물 관리 번호
        sobj.setString("NEW_ADDR1", NEW_ADDR1);               //도로명주소1
        sobj.setString("NEW_ADDR2", NEW_ADDR2);               //도로명주소2
        sobj.setString("NEW_ZIP", NEW_ZIP);               //우편번호
        sobj.setString("NEW_ZIP1", NEW_ZIP1);               //우편번호
        sobj.setString("REF_INFO", REF_INFO);               //참고 항목
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 허가주주소저장
    public DOBJ CALLsave_juso_XIUD20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD20");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_juso_XIUD20(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_juso_XIUD20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BD_MNG_NUM = dobj.getRetObject("R").getRecord().get("BD_MNG_NUM");   //건물 관리 번호
        String   NEW_ADDR1 = dobj.getRetObject("R").getRecord().get("NEW_ADDR1");   //도로명주소1
        String   NEW_ADDR2 = dobj.getRetObject("R").getRecord().get("NEW_ADDR2");   //도로명주소2
        String   NEW_ZIP = dobj.getRetObject("R").getRecord().get("NEW_ZIP");   //우편번호
        String   NEW_ZIP1 = dobj.getRetObject("R").getRecord().get("NEW_ZIP1");   //우편번호
        String   REF_INFO = dobj.getRetObject("R").getRecord().get("REF_INFO");   //참고 항목
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET PERMMSTR_NEW_ZIP = :NEW_ZIP , PERMMSTR_NEW_ZIP1 = :NEW_ZIP1 , PERMMSTR_NEW_ADDR1 = :NEW_ADDR1 , PERMMSTR_NEW_ADDR2 = :NEW_ADDR2 , PERMMSTR_REF_INFO = :REF_INFO , PERMMSTR_BD_MNG_NUM = :BD_MNG_NUM  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BD_MNG_NUM", BD_MNG_NUM);               //건물 관리 번호
        sobj.setString("NEW_ADDR1", NEW_ADDR1);               //도로명주소1
        sobj.setString("NEW_ADDR2", NEW_ADDR2);               //도로명주소2
        sobj.setString("NEW_ZIP", NEW_ZIP);               //우편번호
        sobj.setString("NEW_ZIP1", NEW_ZIP1);               //우편번호
        sobj.setString("REF_INFO", REF_INFO);               //참고 항목
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$save_juso
    //##**$$end
}
