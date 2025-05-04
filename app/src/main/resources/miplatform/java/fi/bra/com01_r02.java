
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class com01_r02
{
    public com01_r02()
    {
    }
    //##**$$sel_bstyp
    /*
    */
    public DOBJ CTLsel_bstyp(DOBJ dobj)
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
            dobj  = CALLsel_bstyp_SEL1(Conn, dobj);           //  업종분류조회
            dobj  = CALLsel_bstyp_MPD4(Conn, dobj);           //  분기
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
    public DOBJ CTLsel_bstyp( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bstyp_SEL1(Conn, dobj);           //  업종분류조회
        dobj  = CALLsel_bstyp_MPD4(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 업종분류조회
    public DOBJ CALLsel_bstyp_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bstyp_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bstyp_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GRAD_GBN  ,  GRADNM  ,  MDM_CD  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  IN   \n";
        query +=" (SELECT  DISTINCT  BSTYP_CD  FROM  GIBU.TBRA_BSTYPGRAD)  ORDER  BY  GRAD_GBN ";
        sobj.setSql(query);
        return sobj;
    }
    // 분기
    public DOBJ CALLsel_bstyp_MPD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD4");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //업종분류조회에서 생성시킨 OBJECT입니다.(CALLsel_bstyp_SEL1)
        dvobj.Println("MPD4");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("MDM_CD").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsel_bstyp_SEL5(Conn, dobj);           //  매체코드명조회
                dobj  = CALLsel_bstyp_ADD8( dobj);        //  매체코드명저장
            }
            else
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsel_bstyp_SEL7(Conn, dobj);           //  세부업종조회
                dobj  = CALLsel_bstyp_ADD9( dobj);        //  세부업종저장
            }
        }
        return dobj;
    }
    // 매체코드명조회
    public DOBJ CALLsel_bstyp_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bstyp_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bstyp_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MDM_CD = dobj.getRetObject("R").getRecord().get("MDM_CD");   //매체 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MDM_CD  ,  MDM_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD  =  :MDM_CD ";
        sobj.setSql(query);
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        return sobj;
    }
    // 세부업종조회
    public DOBJ CALLsel_bstyp_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bstyp_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bstyp_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSTYP_CD = dobj.getRetObject("R").getRecord().get("GRAD_GBN");   //업종 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BSTYP_CD  ||  A.GRAD_GBN  GRAD  ,  A.GRADNM  ,  A.STNDAMT  FROM  GIBU.TBRA_BSTYPGRAD  A  WHERE  A.BSTYP_CD  =  :BSTYP_CD  ORDER  BY  A.BSTYP_CD  ||  A.GRAD_GBN  ASC ";
        sobj.setSql(query);
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        return sobj;
    }
    // 매체코드명저장
    public DOBJ CALLsel_bstyp_ADD8(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD8");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL5");          //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("ADD8");
        rvobj = wutil.getAddedVobj(dobj,"ADD8","MDM_CD, MDM_CD_NM", dvobj );
        rvobj.setName("ADD8");
        rvobj.Println("ADD8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 세부업종저장
    public DOBJ CALLsel_bstyp_ADD9(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD9");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL7");          //사용자 화면에서 발생한 Object입니다.
        rvobj = wutil.getAddedVobj(dobj,"ADD9","GRAD, GRADNM, STNDAMT", dvobj );
        rvobj.setName("ADD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    //##**$$sel_bstyp
    //##**$$sel_bran_staff
    /*
    */
    public DOBJ CTLsel_bran_staff(DOBJ dobj)
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
            dobj  = CALLsel_bran_staff_SEL1(Conn, dobj);           //  지부담당자조회
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
    public DOBJ CTLsel_bran_staff( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bran_staff_SEL1(Conn, dobj);           //  지부담당자조회
        return dobj;
    }
    // 지부담당자조회
    // 담당자 최종 변경목록
    public DOBJ CALLsel_bran_staff_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bran_staff_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bran_staff_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  STAFF_CD,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  PRCON_YRMN  =   \n";
        query +=" (SELECT  MAX(PRCON_YRMN)  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  BRAN_CD  =  :BRAN_CD)  GROUP  BY  BRAN_CD,  STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$sel_bran_staff
    //##**$$end
}
