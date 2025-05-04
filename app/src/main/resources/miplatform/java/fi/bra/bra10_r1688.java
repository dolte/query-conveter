
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r1688
{
    public bra10_r1688()
    {
    }
    //##**$$tracelog_save
    /*
    */
    public DOBJ CTLtracelog_save(DOBJ dobj)
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
            dobj  = CALLtracelog_save_INS1(Conn, dobj);           //  로그저장
            dobj.setRtncode(9);
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
    public DOBJ CTLtracelog_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtracelog_save_INS1(Conn, dobj);           //  로그저장
        dobj.setRtncode(9);
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 로그저장
    // 프로그램의 변경된 컬럼의 내역을 저장한다.
    public DOBJ CALLtracelog_save_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtracelog_save_INS1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS1") ;
        rvobj.Println("INS1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtracelog_save_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double MNG_NUM = 0;        //관리번호
        String   CHGATR_CTENT = dvobj.getRecord().get("CHGATR_CTENT");
        String   MENU_ID = dvobj.getRecord().get("MENU_ID");
        String   IPADDRESS = dvobj.getRecord().get("IPADDRESS");
        String   COL_NM = dvobj.getRecord().get("COL_NM");   //컬럼 명
        String   COMPUTER_NM = dvobj.getRecord().get("COMPUTER_NM");
        String   CHGBFR_CTENT = dvobj.getRecord().get("CHGBFR_CTENT");
        String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TENV_COL_HISTY (INS_DATE, INSPRES_ID, CHGBFR_CTENT, COMPUTER_NM, COL_NM, MNG_NUM, IPADDRESS, MENU_ID, CHGATR_CTENT)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :CHGBFR_CTENT , :COMPUTER_NM , :COL_NM , FIDU.TENV_COL_HISTY_SEQ.NEXTVAL, :IPADDRESS , :MENU_ID , :CHGATR_CTENT )";
        sobj.setSql(query);
        sobj.setString("CHGATR_CTENT", CHGATR_CTENT);
        sobj.setString("MENU_ID", MENU_ID);
        sobj.setString("IPADDRESS", IPADDRESS);
        sobj.setString("COL_NM", COL_NM);               //컬럼 명
        sobj.setString("COMPUTER_NM", COMPUTER_NM);
        sobj.setString("CHGBFR_CTENT", CHGBFR_CTENT);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$tracelog_save
    //##**$$end
}
