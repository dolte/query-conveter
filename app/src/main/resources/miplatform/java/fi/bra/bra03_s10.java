
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s10
{
    public bra03_s10()
    {
    }
    //##**$$indtnpaper_prnt_mng
    /* * 프로그램명 : bra03_s10
    * 작성자 : 박태현
    * 작성일 : 2009/10/22
    * 설명    : 출력이 완료된 항목에 대해 출력여부 flag 를 저장한다.
    Input :
    BRAN_CD (지부 코드)
    CRET_GBN (OCR/MICR 생성 구분)
    DEMD_YRMN (청구 년월)
    */
    public DOBJ CTLindtnpaper_prnt_mng(DOBJ dobj)
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
            dobj  = CALLindtnpaper_prnt_mng_MIUD2(Conn, dobj);           //  관리
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
    public DOBJ CTLindtnpaper_prnt_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtnpaper_prnt_mng_MIUD2(Conn, dobj);           //  관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 관리
    public DOBJ CALLindtnpaper_prnt_mng_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLindtnpaper_prnt_mng_UPD1(Conn, dobj);           //  출력여부저장
            }
        }
        return dobj;
    }
    // 출력여부저장
    // 출력을 하게 되면 출력자 ID, 출력 일자를 로그인한 사람 ID, 출력한 일자로 UPDATE 한다. (업소원장조회의 발송현황정보시 조회조건으로 이용)
    public DOBJ CALLindtnpaper_prnt_mng_UPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLindtnpaper_prnt_mng_UPD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_prnt_mng_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String PRNT_DAY ="";        //출력 일자
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   DEMD_DAY = dvobj.getRecord().get("DEMD_DAY");   //청구 일자
        String   CRET_GBN = dvobj.getRecord().get("CRET_GBN");   //OCR/MICR 생성 구분
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        String   PRNTPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //출력자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_DEMD_INDTN  \n";
        query +=" set PRNTPRES_ID=:PRNTPRES_ID , PRNT_DAY=TO_CHAR(SYSDATE, 'YYYYMMDD')  \n";
        query +=" where DEMD_NUM=:DEMD_NUM  \n";
        query +=" and CRET_GBN=:CRET_GBN  \n";
        query +=" and DEMD_DAY=:DEMD_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("CRET_GBN", CRET_GBN);               //OCR/MICR 생성 구분
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("PRNTPRES_ID", PRNTPRES_ID);               //출력자 ID
        return sobj;
    }
    //##**$$indtnpaper_prnt_mng
    //##**$$end
}
