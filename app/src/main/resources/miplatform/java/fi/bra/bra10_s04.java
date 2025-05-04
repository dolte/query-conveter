
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s04
{
    public bra10_s04()
    {
    }
    //##**$$zip_mng
    /* * 프로그램명 : bra10_s04
    * 작성자 : 서정재
    * 작성일 : 2009/08/12
    * 설명 :
    * 수정일1: 2009/12/24
    * 수정자 :
    * 수정내용 : 수정일경우 수정이 안된다. 조건이 잘못되어 있어서 변경
    */
    public DOBJ CTLzip_mng(DOBJ dobj)
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
            dobj  = CALLzip_mng_MIUD1(Conn, dobj);           //  관리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLzip_mng_SEL16(Conn, dobj);           //  우편번호조회
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
    public DOBJ CTLzip_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLzip_mng_MIUD1(Conn, dobj);           //  관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLzip_mng_SEL16(Conn, dobj);           //  우편번호조회
        return dobj;
    }
    // 관리
    public DOBJ CALLzip_mng_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLzip_mng_INS5(Conn, dobj);           //  등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLzip_mng_UPD12(Conn, dobj);           //  수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLzip_mng_DEL6(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    public DOBJ CALLzip_mng_DEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLzip_mng_DEL6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   POST_SNUM = dvobj.getRecord().get("POST_SNUM");   //우편 일련번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BRANZIP_MNG  \n";
        query +=" where POST_SNUM=:POST_SNUM";
        sobj.setSql(query);
        sobj.setString("POST_SNUM", POST_SNUM);               //우편 일련번호
        return sobj;
    }
    // 등록
    public DOBJ CALLzip_mng_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLzip_mng_INS5(dobj, dvobj);
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
    private SQLObject SQLzip_mng_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String POST_SNUM ="";        //우편 일련번호
        String   ZIP = dvobj.getRecord().get("ZIP");   //우편번호
        String   HNM = dvobj.getRecord().get("HNM");   //번지
        String   DSRCCNTY = dvobj.getRecord().get("DSRCCNTY");   //구군
        String   DONG = dvobj.getRecord().get("DONG");   //동
        String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //관리 우편번호
        String   ATTE = dvobj.getRecord().get("ATTE");   //시도
        String   USE_YN = dvobj.getRecord().get("USE_YN");   //사용구분
        String   ALLADDR = dobj.getRetObject("R").getRecord().get("ATTE")+" "+dobj.getRetObject("R").getRecord().get("DSRCCNTY")+" "+dobj.getRetObject("R").getRecord().get("DONG")+" "+dobj.getRetObject("R").getRecord().get("HNM");   //전체주소
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USE_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BRANZIP_MNG (USE_YN, ATTE, INS_DATE, MNG_ZIP, POST_SNUM, INSPRES_ID, ALLADDR, DONG, DSRCCNTY, HNM, ZIP)  \n";
        query +=" values(:USE_YN , :ATTE , SYSDATE, :MNG_ZIP , (SELECT LPAD(NVL(MAX(POST_SNUM), 0) + 1, 6, '0') POST_SNUM FROM GIBU.TBRA_BRANZIP_MNG), :INSPRES_ID , :ALLADDR , :DONG , :DSRCCNTY , :HNM , :ZIP )";
        sobj.setSql(query);
        sobj.setString("ZIP", ZIP);               //우편번호
        sobj.setString("HNM", HNM);               //번지
        sobj.setString("DSRCCNTY", DSRCCNTY);               //구군
        sobj.setString("DONG", DONG);               //동
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("USE_YN", USE_YN);               //사용구분
        sobj.setString("ALLADDR", ALLADDR);               //전체주소
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 수정
    public DOBJ CALLzip_mng_UPD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLzip_mng_UPD12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_UPD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   ZIP = dvobj.getRecord().get("ZIP");   //우편번호
        String   HNM = dvobj.getRecord().get("HNM");   //번지
        String   DSRCCNTY = dvobj.getRecord().get("DSRCCNTY");   //구군
        String   DONG = dvobj.getRecord().get("DONG");   //동
        String   POST_SNUM = dvobj.getRecord().get("POST_SNUM");   //우편 일련번호
        String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //관리 우편번호
        String   ATTE = dvobj.getRecord().get("ATTE");   //시도
        String   USE_YN = dvobj.getRecord().get("USE_YN");   //사용구분
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USE_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BRANZIP_MNG  \n";
        query +=" set USE_YN=:USE_YN , MODPRES_ID=:MODPRES_ID , ATTE=:ATTE , MNG_ZIP=:MNG_ZIP , DONG=:DONG , DSRCCNTY=:DSRCCNTY , HNM=:HNM , ZIP=:ZIP , MOD_DATE=SYSDATE  \n";
        query +=" where POST_SNUM=:POST_SNUM";
        sobj.setSql(query);
        sobj.setString("ZIP", ZIP);               //우편번호
        sobj.setString("HNM", HNM);               //번지
        sobj.setString("DSRCCNTY", DSRCCNTY);               //구군
        sobj.setString("DONG", DONG);               //동
        sobj.setString("POST_SNUM", POST_SNUM);               //우편 일련번호
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("USE_YN", USE_YN);               //사용구분
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 우편번호조회
    public DOBJ CALLzip_mng_SEL16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL16");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL16");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_mng_SEL16(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL16");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_SEL16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USE_YN = dobj.getRetObject("S1").getRecord().get("USE_YN");   //사용구분
        String   TO_ZIP = dobj.getRetObject("S1").getRecord().get("TO_ZIP");   //우편번호 검색 TO
        String   FROM_ZIP = dobj.getRetObject("S1").getRecord().get("FROM_ZIP");   //우편번호 검색 FROM
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  POST_SNUM  ,  MNG_ZIP  ,  ZIP  ,  ATTE  ,  DSRCCNTY  ,  DONG  ,  HNM  ,  USE_YN  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  ZIP  BETWEEN  :FROM_ZIP   \n";
        query +=" AND  :TO_ZIP   \n";
        query +=" AND  USE_YN  LIKE  :USE_YN  ||  '%'  ORDER  BY  MNG_ZIP ";
        sobj.setSql(query);
        sobj.setString("USE_YN", USE_YN);               //사용구분
        sobj.setString("TO_ZIP", TO_ZIP);               //우편번호 검색 TO
        sobj.setString("FROM_ZIP", FROM_ZIP);               //우편번호 검색 FROM
        return sobj;
    }
    //##**$$zip_mng
    //##**$$zone_search
    /*
    */
    public DOBJ CTLzone_search(DOBJ dobj)
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
            if(!dobj.getRetObject("S").getRecord().get("ATTE").equals("") && !dobj.getRetObject("S").getRecord().get("DSRCCNTY").equals(""))
            {
                dobj  = CALLzone_search_SEL1(Conn, dobj);           //  동 정보조회
            }
            else if(!dobj.getRetObject("S").getRecord().get("ATTE").equals(""))
            {
                dobj  = CALLzone_search_SEL5(Conn, dobj);           //  구군 정보 조회
            }
            else
            {
                dobj  = CALLzone_search_SEL6(Conn, dobj);           //  시도 정보 조회
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
    public DOBJ CTLzone_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if(!dobj.getRetObject("S").getRecord().get("ATTE").equals("") && !dobj.getRetObject("S").getRecord().get("DSRCCNTY").equals(""))
        {
            dobj  = CALLzone_search_SEL1(Conn, dobj);           //  동 정보조회
        }
        else if(!dobj.getRetObject("S").getRecord().get("ATTE").equals(""))
        {
            dobj  = CALLzone_search_SEL5(Conn, dobj);           //  구군 정보 조회
        }
        else
        {
            dobj  = CALLzone_search_SEL6(Conn, dobj);           //  시도 정보 조회
        }
        return dobj;
    }
    // 동 정보조회
    public DOBJ CALLzone_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzone_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzone_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        String   DSRCCNTY = dobj.getRetObject("S").getRecord().get("DSRCCNTY");   //구군
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '전체'  DONG  ,  '000000'  MNG_ZIP  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  DONG  ,  MNG_ZIP  FROM  (   \n";
        query +=" SELECT  MIN(DONG)  DONG  ,  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  ATTE  =  :ATTE   \n";
        query +=" AND  DSRCCNTY  =  :DSRCCNTY  GROUP  BY  MNG_ZIP  ORDER  BY  MNG_ZIP  ) ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("DSRCCNTY", DSRCCNTY);               //구군
        return sobj;
    }
    // 구군 정보 조회
    public DOBJ CALLzone_search_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzone_search_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.setRetcode(1);
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzone_search_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  DSRCCNTY  ,  SUBSTR(MNG_ZIP,1,3)  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  ATTE  =  :ATTE  ORDER  BY  DSRCCNTY ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        return sobj;
    }
    // 시도 정보 조회
    public DOBJ CALLzone_search_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzone_search_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzone_search_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  ATTE  FROM  GIBU.TBRA_BRANZIP_MNG  ORDER  BY  ATTE ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$zone_search
    //##**$$zip_list
    /*
    */
    public DOBJ CTLbran_zip_list(DOBJ dobj)
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
            dobj  = CALLzip_list_SEL1(Conn, dobj);           //  지부별 담당구역 리스트 조회
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
    public DOBJ CTLbran_zip_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLzip_list_SEL1(Conn, dobj);           //  지부별 담당구역 리스트 조회
        return dobj;
    }
    // 지부별 담당구역 리스트 조회
    // 지정된 지부코드를 사용하여 관리중인 담당 구역리스트를 조회한다
    public DOBJ CALLzip_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MNG_ZIP_END = dobj.getRetObject("S").getRecord().get("MNG_ZIP_END");   //관리 우편번호 끝
        String   MNG_ZIP_START = dobj.getRetObject("S").getRecord().get("MNG_ZIP_START");   //관리 우편번호 시작
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  XA.MNG_ZIP  ,  XC.ZIP_START  ,  XC.ZIP_END  ,  XC.ZIP_START  ||  '  ~  '  ||  XC.ZIP_END  ZIP  ,  XA.ATTE  ,  XA.DSRCCNTY  ,  XA.DONG  FROM  GIBU.TBRA_BRANZIP_MNG  XA  ,  GIBU.TBRA_BRANZIP_MNG  XB  ,  (   \n";
        query +=" SELECT  MNG_ZIP  ,  MIN(ZIP)  ZIP_START  ,  MAX(ZIP)  ZIP_END  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  MNG_ZIP  BETWEEN  :MNG_ZIP_START   \n";
        query +=" AND  :MNG_ZIP_END  GROUP  BY  MNG_ZIP  )  XC  WHERE  XA.MNG_ZIP  =  XC.MNG_ZIP   \n";
        query +=" AND  XA.ZIP  =  XC.ZIP_START   \n";
        query +=" AND  XB.MNG_ZIP  =  XC.MNG_ZIP   \n";
        query +=" AND  XB.ZIP  =  XC.ZIP_END  ORDER  BY  MNG_ZIP ";
        sobj.setSql(query);
        sobj.setString("MNG_ZIP_END", MNG_ZIP_END);               //관리 우편번호 끝
        sobj.setString("MNG_ZIP_START", MNG_ZIP_START);               //관리 우편번호 시작
        return sobj;
    }
    //##**$$zip_list
    //##**$$bran_cd_mng
    /* * 프로그램명 : bra10_s04
    * 작성자 : 박태현
    * 작성일 : 2009/11/27
    * 설명 : 지부 변경시 사용한다.
    관리우편별로 변경 혹은 지부대 지부를 변경할수도 있다.
    GBN:1 - 해당 관리우편구역별 변경
    GBN:2 - 지부->지부로 변경
    * 수정일1: 2010/02/17
    * 수정자 :
    * 수정내용 : TBRA_REPT_BALANCE, TBRA_NOTE 테이블에 BRAN_CD 추가 ==> 지부변경시 이 테이블도 변경해줘야 한다.
    * 오픈시 이행사항
    현재 CS에는 강남지부(B,C), 충청지부(H,I), 호남지부(K,J) 로 분리되어 있으나 SI에서는 통합시켜줘야함
    강남 ORG_BRAN: C -> BRAN_CD: B / 충청 ORG_BRAN:H -> BRAN_CD:I / 호남 ORG_BRAN:J -> BRAN_CD: K 로 작업해줌
    */
    public DOBJ CTLbran_cd_mng(DOBJ dobj)
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
            dobj  = CALLbran_cd_mng_SEL25(Conn, dobj);           //  분리
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLbran_cd_mng_SEL29(Conn, dobj);           //  변경사항 조회
                dobj  = CALLbran_cd_mng_INS30(Conn, dobj);           //  변경사항 저장
                dobj  = CALLbran_cd_mng_XIUD31(Conn, dobj);           //  업소 지부변경
                dobj  = CALLbran_cd_mng_XIUD32(Conn, dobj);           //  녹취파일 지부변경
                dobj  = CALLbran_cd_mng_XIUD34(Conn, dobj);           //  개별 청구 지부 변경
                dobj  = CALLbran_cd_mng_XIUD35(Conn, dobj);           //  자동이체 청구 지부변경
                dobj  = CALLbran_cd_mng_XIUD36(Conn, dobj);           //  지로 청구 지부변경
                dobj  = CALLbran_cd_mng_XIUD37(Conn, dobj);           //  지로 출력결과 지부변경
                dobj  = CALLbran_cd_mng_XIUD107(Conn, dobj);           //  지로 출력결과 지부변경
                dobj  = CALLbran_cd_mng_XIUD38(Conn, dobj);           //  지로 입금 지부변경
                dobj  = CALLbran_cd_mng_XIUD39(Conn, dobj);           //  자동이체 입금 지부변경
                dobj  = CALLbran_cd_mng_XIUD40(Conn, dobj);           //  카드 입금 지부변경
                dobj  = CALLbran_cd_mng_XIUD41(Conn, dobj);           //  무통장 입금 지부변경
                dobj  = CALLbran_cd_mng_XIUD42(Conn, dobj);           //  입금 지부변경
                dobj  = CALLbran_cd_mng_XIUD43(Conn, dobj);           //  입금 에러 지부변경
                dobj  = CALLbran_cd_mng_XIUD44(Conn, dobj);           //  입금 지부 분배 지부변경
                dobj  = CALLbran_cd_mng_XIUD66(Conn, dobj);           //  입금 지부 분배 지부변경
                dobj  = CALLbran_cd_mng_XIUD54(Conn, dobj);           //  입금증관리 지부변경
                dobj  = CALLbran_cd_mng_XIUD57(Conn, dobj);           //  입금반환관리 지부변경
                dobj  = CALLbran_cd_mng_XIUD61(Conn, dobj);           //  입금잔고 지부정보 변경
                dobj  = CALLbran_cd_mng_XIUD104(Conn, dobj);           //  출장정보 지부정보 변경
                dobj  = CALLbran_cd_mng_XIUD105(Conn, dobj);           //  출장정보 첨부 지부정보 변경
                dobj  = CALLbran_cd_mng_XIUD63(Conn, dobj);           //  온라인금영로그기통계
                dobj  = CALLbran_cd_mng_XIUD68(Conn, dobj);           //  원장 지부정보 변경
                dobj  = CALLbran_cd_mng_XIUD70(Conn, dobj);           //  지부입금마감 삭제
                dobj  = CALLbran_cd_mng_SEL58(Conn, dobj);           //  최종 변경정보 조회
            }
            else
            {
                dobj  = CALLbran_cd_mng_SEL9(Conn, dobj);           //  변경사항 조회
                dobj  = CALLbran_cd_mng_INS10(Conn, dobj);           //  변경사항 저장
                dobj  = CALLbran_cd_mng_XIUD3(Conn, dobj);           //  업소 지부변경
                dobj  = CALLbran_cd_mng_XIUD12(Conn, dobj);           //  녹취파일 지부변경
                dobj  = CALLbran_cd_mng_XIUD14(Conn, dobj);           //  개별 청구 지부 변경
                dobj  = CALLbran_cd_mng_XIUD15(Conn, dobj);           //  자동이체 청구 지부변경
                dobj  = CALLbran_cd_mng_XIUD16(Conn, dobj);           //  지로 청구 지부변경
                dobj  = CALLbran_cd_mng_XIUD17(Conn, dobj);           //  지로 출력결과 지부변경
                dobj  = CALLbran_cd_mng_XIUD18(Conn, dobj);           //  지로 입금 지부변경
                dobj  = CALLbran_cd_mng_XIUD19(Conn, dobj);           //  자동이체 입금 지부변경
                dobj  = CALLbran_cd_mng_XIUD20(Conn, dobj);           //  카드 입금 지부변경
                dobj  = CALLbran_cd_mng_XIUD21(Conn, dobj);           //  무통장 입금 지부변경
                dobj  = CALLbran_cd_mng_XIUD22(Conn, dobj);           //  입금 지부변경
                dobj  = CALLbran_cd_mng_XIUD23(Conn, dobj);           //  입금 에러 지부변경
                dobj  = CALLbran_cd_mng_XIUD24(Conn, dobj);           //  입금 지부 분배 지부변경
                dobj  = CALLbran_cd_mng_XIUD56(Conn, dobj);           //  입금증관리 지부변경
                dobj  = CALLbran_cd_mng_XIUD58(Conn, dobj);           //  입금반환관리 지부변경
                dobj  = CALLbran_cd_mng_XIUD62(Conn, dobj);           //  입금잔고 지부정보 변경
                dobj  = CALLbran_cd_mng_XIUD69(Conn, dobj);           //  원장 지부정보 변경
                dobj  = CALLbran_cd_mng_SEL11(Conn, dobj);           //  최종 변경정보 조회
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
    public DOBJ CTLbran_cd_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbran_cd_mng_SEL25(Conn, dobj);           //  분리
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLbran_cd_mng_SEL29(Conn, dobj);           //  변경사항 조회
            dobj  = CALLbran_cd_mng_INS30(Conn, dobj);           //  변경사항 저장
            dobj  = CALLbran_cd_mng_XIUD31(Conn, dobj);           //  업소 지부변경
            dobj  = CALLbran_cd_mng_XIUD32(Conn, dobj);           //  녹취파일 지부변경
            dobj  = CALLbran_cd_mng_XIUD34(Conn, dobj);           //  개별 청구 지부 변경
            dobj  = CALLbran_cd_mng_XIUD35(Conn, dobj);           //  자동이체 청구 지부변경
            dobj  = CALLbran_cd_mng_XIUD36(Conn, dobj);           //  지로 청구 지부변경
            dobj  = CALLbran_cd_mng_XIUD37(Conn, dobj);           //  지로 출력결과 지부변경
            dobj  = CALLbran_cd_mng_XIUD107(Conn, dobj);           //  지로 출력결과 지부변경
            dobj  = CALLbran_cd_mng_XIUD38(Conn, dobj);           //  지로 입금 지부변경
            dobj  = CALLbran_cd_mng_XIUD39(Conn, dobj);           //  자동이체 입금 지부변경
            dobj  = CALLbran_cd_mng_XIUD40(Conn, dobj);           //  카드 입금 지부변경
            dobj  = CALLbran_cd_mng_XIUD41(Conn, dobj);           //  무통장 입금 지부변경
            dobj  = CALLbran_cd_mng_XIUD42(Conn, dobj);           //  입금 지부변경
            dobj  = CALLbran_cd_mng_XIUD43(Conn, dobj);           //  입금 에러 지부변경
            dobj  = CALLbran_cd_mng_XIUD44(Conn, dobj);           //  입금 지부 분배 지부변경
            dobj  = CALLbran_cd_mng_XIUD66(Conn, dobj);           //  입금 지부 분배 지부변경
            dobj  = CALLbran_cd_mng_XIUD54(Conn, dobj);           //  입금증관리 지부변경
            dobj  = CALLbran_cd_mng_XIUD57(Conn, dobj);           //  입금반환관리 지부변경
            dobj  = CALLbran_cd_mng_XIUD61(Conn, dobj);           //  입금잔고 지부정보 변경
            dobj  = CALLbran_cd_mng_XIUD104(Conn, dobj);           //  출장정보 지부정보 변경
            dobj  = CALLbran_cd_mng_XIUD105(Conn, dobj);           //  출장정보 첨부 지부정보 변경
            dobj  = CALLbran_cd_mng_XIUD63(Conn, dobj);           //  온라인금영로그기통계
            dobj  = CALLbran_cd_mng_XIUD68(Conn, dobj);           //  원장 지부정보 변경
            dobj  = CALLbran_cd_mng_XIUD70(Conn, dobj);           //  지부입금마감 삭제
            dobj  = CALLbran_cd_mng_SEL58(Conn, dobj);           //  최종 변경정보 조회
        }
        else
        {
            dobj  = CALLbran_cd_mng_SEL9(Conn, dobj);           //  변경사항 조회
            dobj  = CALLbran_cd_mng_INS10(Conn, dobj);           //  변경사항 저장
            dobj  = CALLbran_cd_mng_XIUD3(Conn, dobj);           //  업소 지부변경
            dobj  = CALLbran_cd_mng_XIUD12(Conn, dobj);           //  녹취파일 지부변경
            dobj  = CALLbran_cd_mng_XIUD14(Conn, dobj);           //  개별 청구 지부 변경
            dobj  = CALLbran_cd_mng_XIUD15(Conn, dobj);           //  자동이체 청구 지부변경
            dobj  = CALLbran_cd_mng_XIUD16(Conn, dobj);           //  지로 청구 지부변경
            dobj  = CALLbran_cd_mng_XIUD17(Conn, dobj);           //  지로 출력결과 지부변경
            dobj  = CALLbran_cd_mng_XIUD18(Conn, dobj);           //  지로 입금 지부변경
            dobj  = CALLbran_cd_mng_XIUD19(Conn, dobj);           //  자동이체 입금 지부변경
            dobj  = CALLbran_cd_mng_XIUD20(Conn, dobj);           //  카드 입금 지부변경
            dobj  = CALLbran_cd_mng_XIUD21(Conn, dobj);           //  무통장 입금 지부변경
            dobj  = CALLbran_cd_mng_XIUD22(Conn, dobj);           //  입금 지부변경
            dobj  = CALLbran_cd_mng_XIUD23(Conn, dobj);           //  입금 에러 지부변경
            dobj  = CALLbran_cd_mng_XIUD24(Conn, dobj);           //  입금 지부 분배 지부변경
            dobj  = CALLbran_cd_mng_XIUD56(Conn, dobj);           //  입금증관리 지부변경
            dobj  = CALLbran_cd_mng_XIUD58(Conn, dobj);           //  입금반환관리 지부변경
            dobj  = CALLbran_cd_mng_XIUD62(Conn, dobj);           //  입금잔고 지부정보 변경
            dobj  = CALLbran_cd_mng_XIUD69(Conn, dobj);           //  원장 지부정보 변경
            dobj  = CALLbran_cd_mng_SEL11(Conn, dobj);           //  최종 변경정보 조회
        }
        return dobj;
    }
    // 분리
    public DOBJ CALLbran_cd_mng_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_cd_mng_SEL25(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //구분
        return sobj;
    }
    // 변경사항 조회
    // 변경이 발생할 사항을 조회한다
    public DOBJ CALLbran_cd_mng_SEL29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL29");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL29");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_cd_mng_SEL29(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL29");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_SEL29(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //ORG_BRAN_CD
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROWNUM  +  B.CHG_SEQ  AS  CHG_SEQ  ,  A.UPSO_CD  AS  UPSO_CD  ,  :ORG_BRAN_CD  AS  ORG_BRAN_CD  ,  :BRAN_CD  AS  CHG_BRAN_CD  ,  :INSPRES_ID  AS  INSPRES_ID  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  NVL(MAX(CHG_SEQ),  0)  CHG_SEQ  FROM  GIBU.TBRA_BRANCHG_HISTY  )  B  WHERE  A.BRAN_CD  =  :ORG_BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //ORG_BRAN_CD
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 변경사항 저장
    // 변경된 사항의 이력정보를 저장한다
    public DOBJ CALLbran_cd_mng_INS30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS30");
        VOBJ dvobj = dobj.getRetObject("SEL29");           //변경사항 조회에서 생성시킨 OBJECT입니다.(CALLbran_cd_mng_SEL29)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_INS30(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS30") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_INS30(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CHG_SEQ = dvobj.getRecord().get("CHG_SEQ");
        String   CHG_BRAN_CD = dvobj.getRecord().get("CHG_BRAN_CD");
        String   ORG_BRAN_CD = dvobj.getRecord().get("ORG_BRAN_CD");   //변경전지부
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BRANCHG_HISTY (INS_DATE, ORG_BRAN_CD, INSPRES_ID, CHG_BRAN_CD, CHG_SEQ, UPSO_CD)  \n";
        query +=" values(SYSDATE, :ORG_BRAN_CD , :INSPRES_ID , :CHG_BRAN_CD , :CHG_SEQ , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CHG_SEQ", CHG_SEQ);
        sobj.setString("CHG_BRAN_CD", CHG_BRAN_CD);
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 업소 지부변경
    // 업소의 지부를 변경한다
    public DOBJ CALLbran_cd_mng_XIUD31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD31");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD31(dobj, dvobj);
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
    private SQLObject SQLbran_cd_mng_XIUD31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 녹취파일 지부변경
    public DOBJ CALLbran_cd_mng_XIUD32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD32");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD32(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD32");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD32(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_ADRS_FILEINFO  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 개별 청구 지부 변경
    public DOBJ CALLbran_cd_mng_XIUD34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD34");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD34(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD34");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_INDTN  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 자동이체 청구 지부변경
    public DOBJ CALLbran_cd_mng_XIUD35(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD35");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD35(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD35");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD35(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_AUTO  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 지로 청구 지부변경
    public DOBJ CALLbran_cd_mng_XIUD36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD36");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD36(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD36");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_OCR  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 지로 출력결과 지부변경
    public DOBJ CALLbran_cd_mng_XIUD37(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD37");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD37(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD37");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD37(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_OCR_PRNT_RSLT  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 지로 출력결과 지부변경
    public DOBJ CALLbran_cd_mng_XIUD107(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD107");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD107(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD107");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD107(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_CARD  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 지로 입금 지부변경
    public DOBJ CALLbran_cd_mng_XIUD38(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD38");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD38(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD38");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD38(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_OCR  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 자동이체 입금 지부변경
    public DOBJ CALLbran_cd_mng_XIUD39(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD39");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD39(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD39");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD39(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_AUTO  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 카드 입금 지부변경
    public DOBJ CALLbran_cd_mng_XIUD40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD40");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD40(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_CARD  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 무통장 입금 지부변경
    public DOBJ CALLbran_cd_mng_XIUD41(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD41");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD41(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD41");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD41(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_TRANS  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 입금 지부변경
    public DOBJ CALLbran_cd_mng_XIUD42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD42");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD42(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD42");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD42(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 입금 에러 지부변경
    public DOBJ CALLbran_cd_mng_XIUD43(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD43");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD43(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD43");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD43(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_ERR  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 입금 지부 분배 지부변경
    public DOBJ CALLbran_cd_mng_XIUD44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD44");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD44(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD44");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_DISTR  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 입금 지부 분배 지부변경
    public DOBJ CALLbran_cd_mng_XIUD66(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD66");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD66(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD66");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD66(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_UPSO_DISTR  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 입금증관리 지부변경
    public DOBJ CALLbran_cd_mng_XIUD54(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD54");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD54(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD54");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD54(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_ACK_ISS  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 입금반환관리 지부변경
    public DOBJ CALLbran_cd_mng_XIUD57(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD57");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD57(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD57");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD57(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_RETURN  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 입금잔고 지부정보 변경
    public DOBJ CALLbran_cd_mng_XIUD61(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD61");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD61(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD61");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD61(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_BALANCE  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 출장정보 지부정보 변경
    public DOBJ CALLbran_cd_mng_XIUD104(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD104");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD104(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD104");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD104(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BTRIP  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 출장정보 첨부 지부정보 변경
    public DOBJ CALLbran_cd_mng_XIUD105(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD105");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD105(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD105");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD105(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BTRIP_DOC_ATTCH  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 온라인금영로그기통계
    public DOBJ CALLbran_cd_mng_XIUD63(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD63");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD63(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD63");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD63(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE KDS_STATISTICS  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 원장 지부정보 변경
    public DOBJ CALLbran_cd_mng_XIUD68(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD68");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD68(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD68");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD68(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_NOTE  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 지부입금마감 삭제
    // 지부->지부 로 변경일 경우 이전 지부의 입금마감 정보는 필요없기 때문에 이전지부의 데이타는 삭제한다
    public DOBJ CALLbran_cd_mng_XIUD70(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD70");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD70(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD70");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD70(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_BRANEND  \n";
        query +=" WHERE  \n";
        query +=" BRAN_CD=:ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 최종 변경정보 조회
    // 최종 변경정보 조회
    public DOBJ CALLbran_cd_mng_SEL58(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL58");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL58");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_cd_mng_SEL58(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL58");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_SEL58(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.CHG_SEQ  ,  ROWNUM  AS  SEQ  ,  A.MNG_ZIP  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  C.DEPT_NM  ||  '('  ||  A.ORG_BRAN_CD  ||  ')'  ORG_BRAN_CD  ,  D.DEPT_NM  ||  '('  ||  A.CHG_BRAN_CD  ||  ')'  CHG_BRAN_CD  ,  DECODE(E.HAN_NM,  NULL,  A.INSPRES_ID,  E.HAN_NM||'('||A.INSPRES_ID||')')  INSPRES_ID  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  INS_DATE  FROM  GIBU.TBRA_BRANCHG_HISTY  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_DEPT  D  ,  INSA.TINS_MST01  E  WHERE  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.GIBU(+)  =  A.ORG_BRAN_CD   \n";
        query +=" AND  D.GIBU(+)  =  A.CHG_BRAN_CD   \n";
        query +=" AND  E.STAFF_NUM(+)  =  A.INSPRES_ID ";
        sobj.setSql(query);
        return sobj;
    }
    // 변경사항 조회
    // 변경이 발생할 사항을 조회한다
    public DOBJ CALLbran_cd_mng_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_cd_mng_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROWNUM  +  B.CHG_SEQ  AS  CHG_SEQ  ,  :MNG_ZIP  AS  MNG_ZIP  ,  A.UPSO_CD  AS  UPSO_CD  ,  A.BRAN_CD  AS  ORG_BRAN_CD  ,  :BRAN_CD  AS  CHG_BRAN_CD  ,  :INSPRES_ID  AS  INSPRES_ID  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  NVL(MAX(CHG_SEQ),  0)  CHG_SEQ  FROM  GIBU.TBRA_BRANCHG_HISTY  )  B  WHERE  SUBSTR(A.UPSO_BD_MNG_NUM,  1,5)  =  :MNG_ZIP ";
        sobj.setSql(query);
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 변경사항 저장
    // 변경된 사항의 이력정보를 저장한다
    public DOBJ CALLbran_cd_mng_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("SEL9");           //변경사항 조회에서 생성시킨 OBJECT입니다.(CALLbran_cd_mng_SEL9)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_INS10(dobj, dvobj);
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
    private SQLObject SQLbran_cd_mng_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CHG_SEQ = dvobj.getRecord().get("CHG_SEQ");
        String   CHG_BRAN_CD = dvobj.getRecord().get("CHG_BRAN_CD");
        String   ORG_BRAN_CD = dvobj.getRecord().get("ORG_BRAN_CD");   //변경전지부
        String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //관리 우편번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BRANCHG_HISTY (INS_DATE, MNG_ZIP, ORG_BRAN_CD, INSPRES_ID, CHG_BRAN_CD, CHG_SEQ, UPSO_CD)  \n";
        query +=" values(SYSDATE, :MNG_ZIP , :ORG_BRAN_CD , :INSPRES_ID , :CHG_BRAN_CD , :CHG_SEQ , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CHG_SEQ", CHG_SEQ);
        sobj.setString("CHG_BRAN_CD", CHG_BRAN_CD);
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 업소 지부변경
    // 업소의 지부를 변경한다
    public DOBJ CALLbran_cd_mng_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 녹취파일 지부변경
    public DOBJ CALLbran_cd_mng_XIUD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD12");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_ADRS_FILEINFO  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 개별 청구 지부 변경
    public DOBJ CALLbran_cd_mng_XIUD14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD14");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_INDTN  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 자동이체 청구 지부변경
    public DOBJ CALLbran_cd_mng_XIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD15");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD15(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_AUTO  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 지로 청구 지부변경
    public DOBJ CALLbran_cd_mng_XIUD16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD16");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD16");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_OCR  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 지로 출력결과 지부변경
    public DOBJ CALLbran_cd_mng_XIUD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD17");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_OCR_PRNT_RSLT  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        return sobj;
    }
    // 지로 입금 지부변경
    public DOBJ CALLbran_cd_mng_XIUD18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD18");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_OCR  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 자동이체 입금 지부변경
    public DOBJ CALLbran_cd_mng_XIUD19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD19");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD19(dobj, dvobj);
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
    private SQLObject SQLbran_cd_mng_XIUD19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_AUTO  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 카드 입금 지부변경
    public DOBJ CALLbran_cd_mng_XIUD20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD20");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD20(dobj, dvobj);
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
    private SQLObject SQLbran_cd_mng_XIUD20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_CARD  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 무통장 입금 지부변경
    public DOBJ CALLbran_cd_mng_XIUD21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD21");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD21(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_TRANS  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE MNG_ZIP = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 입금 지부변경
    public DOBJ CALLbran_cd_mng_XIUD22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD22");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD22(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD22");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 입금 에러 지부변경
    public DOBJ CALLbran_cd_mng_XIUD23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD23");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD23(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD23");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_ERR  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 입금 지부 분배 지부변경
    public DOBJ CALLbran_cd_mng_XIUD24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD24");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD24(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD24");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_DISTR  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD IN ( SELECT BRAN_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 입금증관리 지부변경
    public DOBJ CALLbran_cd_mng_XIUD56(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD56");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD56(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD56");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD56(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_ACK_ISS  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD IN ( SELECT BRAN_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        return sobj;
    }
    // 입금반환관리 지부변경
    public DOBJ CALLbran_cd_mng_XIUD58(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD58");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD58(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD58");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD58(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_RETURN  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD IN ( SELECT BRAN_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        return sobj;
    }
    // 입금잔고 지부정보 변경
    public DOBJ CALLbran_cd_mng_XIUD62(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD62");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD62(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD62");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD62(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_BALANCE  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD IN ( SELECT BRAN_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        return sobj;
    }
    // 원장 지부정보 변경
    public DOBJ CALLbran_cd_mng_XIUD69(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD69");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_XIUD69(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD69");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_XIUD69(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_NOTE  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE BRAN_CD IN ( SELECT BRAN_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        return sobj;
    }
    // 최종 변경정보 조회
    // 최종 변경정보 조회
    public DOBJ CALLbran_cd_mng_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_cd_mng_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.CHG_SEQ  ,  ROWNUM  AS  SEQ  ,  A.MNG_ZIP  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  C.DEPT_NM  ||  '('  ||  A.ORG_BRAN_CD  ||  ')'  ORG_BRAN_CD  ,  D.DEPT_NM  ||  '('  ||  A.CHG_BRAN_CD  ||  ')'  CHG_BRAN_CD  ,  DECODE(E.HAN_NM,  NULL,  A.INSPRES_ID,  E.HAN_NM||'('||A.INSPRES_ID||')')  INSPRES_ID  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  INS_DATE  FROM  GIBU.TBRA_BRANCHG_HISTY  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_DEPT  D  ,  INSA.TINS_MST01  E  WHERE  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.GIBU(+)  =  A.ORG_BRAN_CD   \n";
        query +=" AND  D.GIBU(+)  =  A.CHG_BRAN_CD   \n";
        query +=" AND  E.STAFF_NUM(+)  =  A.INSPRES_ID ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$bran_cd_mng
    //##**$$c_zip_search
    /* * 프로그램명 : bra10_s04
    * 작성자 : 서정재
    * 작성일 : 2009/08/28
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLc_zip_search(DOBJ dobj)
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
            dobj  = CALLc_zip_search_SEL1(Conn, dobj);           //  우편번호검색
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
    public DOBJ CTLc_zip_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLc_zip_search_SEL1(Conn, dobj);           //  우편번호검색
        return dobj;
    }
    // 우편번호검색
    // 우편번호검색
    public DOBJ CALLc_zip_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLc_zip_search_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLc_zip_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        String   ZIP = dobj.getRetObject("S").getRecord().get("ZIP");   //우편번호
        String   VARCHAR_TMP1 =" ";   //VARCHAR_TEMP1
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MNG_ZIP  ,  ZIP  ,  ALLADDR  ,  ATTE||:VARCHAR_TMP1||DSRCCNTY||:VARCHAR_TMP1||DONG  ADDR1  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  ZIP  LIKE  :ZIP||'%'   \n";
        query +=" AND  (ATTE  LIKE  '%'||:ATTE||'%'   \n";
        query +=" OR  DSRCCNTY  LIKE  '%'||:ATTE||'%'   \n";
        query +=" OR  DONG  LIKE  '%'||:ATTE||'%')   \n";
        query +=" AND  USE_YN  =  'Y'  ORDER  BY  ALLADDR ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("ZIP", ZIP);               //우편번호
        sobj.setString("VARCHAR_TMP1", VARCHAR_TMP1);               //VARCHAR_TEMP1
        return sobj;
    }
    //##**$$c_zip_search
    //##**$$zone_search_t
    /*
    */
    public DOBJ CTLzone_search_t(DOBJ dobj)
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
            if(!dobj.getRetObject("S").getRecord().get("ATTE").equals("") && !dobj.getRetObject("S").getRecord().get("DSRCCNTY").equals(""))
            {
                dobj  = CALLzone_search_t_SEL1(Conn, dobj);           //  동 정보조회
            }
            else if(!dobj.getRetObject("S").getRecord().get("ATTE").equals(""))
            {
                dobj  = CALLzone_search_t_SEL5(Conn, dobj);           //  구군 정보 조회
            }
            else
            {
                dobj  = CALLzone_search_t_SEL6(Conn, dobj);           //  시도 정보 조회
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
    public DOBJ CTLzone_search_t( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if(!dobj.getRetObject("S").getRecord().get("ATTE").equals("") && !dobj.getRetObject("S").getRecord().get("DSRCCNTY").equals(""))
        {
            dobj  = CALLzone_search_t_SEL1(Conn, dobj);           //  동 정보조회
        }
        else if(!dobj.getRetObject("S").getRecord().get("ATTE").equals(""))
        {
            dobj  = CALLzone_search_t_SEL5(Conn, dobj);           //  구군 정보 조회
        }
        else
        {
            dobj  = CALLzone_search_t_SEL6(Conn, dobj);           //  시도 정보 조회
        }
        return dobj;
    }
    // 동 정보조회
    public DOBJ CALLzone_search_t_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzone_search_t_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzone_search_t_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        String   DSRCCNTY = dobj.getRetObject("S").getRecord().get("DSRCCNTY");   //구군
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '전체'  DONG  ,  '00000'  MNG_ZIP  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  DONG  ,  MNG_ZIP  FROM  (   \n";
        query +=" SELECT  DISTINCT  DECODE(TOWNTWSHP,  '',  COURT_NM,  TOWNTWSHP)  DONG  ,  SUBSTR(BD_MNG_NUM,1,5)  MNG_ZIP  FROM  FIDU.TENV_POST  WHERE  ATTE  =  :ATTE   \n";
        query +=" AND  CITYCNTYDSRC  =  :DSRCCNTY  ) ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("DSRCCNTY", DSRCCNTY);               //구군
        return sobj;
    }
    // 구군 정보 조회
    public DOBJ CALLzone_search_t_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzone_search_t_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.setRetcode(1);
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzone_search_t_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  CITYCNTYDSRC  ,  SUBSTR(BD_MNG_NUM,1,5)  MNG_ZIP  FROM  FIDU.TENV_POST  WHERE  ATTE  =  :ATTE  ORDER  BY  CITYCNTYDSRC ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        return sobj;
    }
    // 시도 정보 조회
    public DOBJ CALLzone_search_t_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzone_search_t_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzone_search_t_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00396'  ORDER  BY  CODE_NM ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$zone_search_t
    //##**$$bran_cd_mng_t
    /*
    */
    public DOBJ CTLbran_cd_mng_t(DOBJ dobj)
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
            dobj  = CALLbran_cd_mng_t_SEL25(Conn, dobj);           //  분리
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLbran_cd_mng_t_SEL29(Conn, dobj);           //  변경사항 조회
                dobj  = CALLbran_cd_mng_t_INS30(Conn, dobj);           //  변경사항 저장
                dobj  = CALLbran_cd_mng_t_XIUD31(Conn, dobj);           //  업소 지부변경
                dobj  = CALLbran_cd_mng_t_SEL58(Conn, dobj);           //  최종 변경정보 조회
            }
            else
            {
                dobj  = CALLbran_cd_mng_t_SEL9(Conn, dobj);           //  변경사항 조회
                dobj  = CALLbran_cd_mng_t_INS10(Conn, dobj);           //  변경사항 저장
                dobj  = CALLbran_cd_mng_t_XIUD3(Conn, dobj);           //  업소 지부변경
                dobj  = CALLbran_cd_mng_t_SEL11(Conn, dobj);           //  최종 변경정보 조회
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
    public DOBJ CTLbran_cd_mng_t( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbran_cd_mng_t_SEL25(Conn, dobj);           //  분리
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLbran_cd_mng_t_SEL29(Conn, dobj);           //  변경사항 조회
            dobj  = CALLbran_cd_mng_t_INS30(Conn, dobj);           //  변경사항 저장
            dobj  = CALLbran_cd_mng_t_XIUD31(Conn, dobj);           //  업소 지부변경
            dobj  = CALLbran_cd_mng_t_SEL58(Conn, dobj);           //  최종 변경정보 조회
        }
        else
        {
            dobj  = CALLbran_cd_mng_t_SEL9(Conn, dobj);           //  변경사항 조회
            dobj  = CALLbran_cd_mng_t_INS10(Conn, dobj);           //  변경사항 저장
            dobj  = CALLbran_cd_mng_t_XIUD3(Conn, dobj);           //  업소 지부변경
            dobj  = CALLbran_cd_mng_t_SEL11(Conn, dobj);           //  최종 변경정보 조회
        }
        return dobj;
    }
    // 분리
    public DOBJ CALLbran_cd_mng_t_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_cd_mng_t_SEL25(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_t_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //구분
        return sobj;
    }
    // 변경사항 조회
    // 변경이 발생할 사항을 조회한다
    public DOBJ CALLbran_cd_mng_t_SEL29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL29");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL29");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_cd_mng_t_SEL29(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL29");
        rvobj.Println("SEL29");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_t_SEL29(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //ORG_BRAN_CD
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROWNUM  +  B.CHG_SEQ  AS  CHG_SEQ  ,  A.UPSO_CD  AS  UPSO_CD  ,  :ORG_BRAN_CD  AS  ORG_BRAN_CD  ,  :BRAN_CD  AS  CHG_BRAN_CD  ,  :INSPRES_ID  AS  INSPRES_ID  FROM  GIBU.TBRA_UPSO_20140103  A  ,  (   \n";
        query +=" SELECT  NVL(MAX(CHG_SEQ),  0)  CHG_SEQ  FROM  GIBU.TBRA_BRANCHG_HISTY  )  B  WHERE  A.BRAN_CD  =  :ORG_BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //ORG_BRAN_CD
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 변경사항 저장
    // 변경된 사항의 이력정보를 저장한다
    public DOBJ CALLbran_cd_mng_t_INS30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS30");
        VOBJ dvobj = dobj.getRetObject("SEL29");           //변경사항 조회에서 생성시킨 OBJECT입니다.(CALLbran_cd_mng_t_SEL29)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_t_INS30(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS30") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_t_INS30(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CHG_SEQ = dvobj.getRecord().get("CHG_SEQ");
        String   CHG_BRAN_CD = dvobj.getRecord().get("CHG_BRAN_CD");
        String   ORG_BRAN_CD = dvobj.getRecord().get("ORG_BRAN_CD");   //변경전지부
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BRANCHG_HISTY (INS_DATE, ORG_BRAN_CD, INSPRES_ID, CHG_BRAN_CD, CHG_SEQ, UPSO_CD)  \n";
        query +=" values(SYSDATE, :ORG_BRAN_CD , :INSPRES_ID , :CHG_BRAN_CD , :CHG_SEQ , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CHG_SEQ", CHG_SEQ);
        sobj.setString("CHG_BRAN_CD", CHG_BRAN_CD);
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 업소 지부변경
    // 업소의 지부를 변경한다
    public DOBJ CALLbran_cd_mng_t_XIUD31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD31");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_t_XIUD31(dobj, dvobj);
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
    private SQLObject SQLbran_cd_mng_t_XIUD31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   ORG_BRAN_CD = dobj.getRetObject("S").getRecord().get("ORG_BRAN_CD");   //변경전지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_20140103  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD=  \n";
        query +=" :ORG_BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        return sobj;
    }
    // 최종 변경정보 조회
    // 최종 변경정보 조회
    public DOBJ CALLbran_cd_mng_t_SEL58(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL58");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL58");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_cd_mng_t_SEL58(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL58");
        rvobj.setRetcode(1);
        rvobj.Println("SEL58");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_t_SEL58(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.CHG_SEQ  ,  ROWNUM  AS  SEQ  ,  A.MNG_ZIP  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  C.DEPT_NM  ||  '('  ||  A.ORG_BRAN_CD  ||  ')'  ORG_BRAN_CD  ,  D.DEPT_NM  ||  '('  ||  A.CHG_BRAN_CD  ||  ')'  CHG_BRAN_CD  ,  DECODE(E.HAN_NM,  NULL,  A.INSPRES_ID,  E.HAN_NM||'('||A.INSPRES_ID||')')  INSPRES_ID  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  INS_DATE  FROM  GIBU.TBRA_BRANCHG_HISTY  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_DEPT  D  ,  INSA.TINS_MST01  E  WHERE  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.GIBU(+)  =  A.ORG_BRAN_CD   \n";
        query +=" AND  D.GIBU(+)  =  A.CHG_BRAN_CD   \n";
        query +=" AND  E.STAFF_NUM(+)  =  A.INSPRES_ID ";
        sobj.setSql(query);
        return sobj;
    }
    // 변경사항 조회
    // 변경이 발생할 사항을 조회한다
    public DOBJ CALLbran_cd_mng_t_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_cd_mng_t_SEL9(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        rvobj.setRetcode(1);
        rvobj.Println("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_t_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROWNUM  +  B.CHG_SEQ  AS  CHG_SEQ  ,  :MNG_ZIP  AS  MNG_ZIP  ,  A.UPSO_CD  AS  UPSO_CD  ,  A.BRAN_CD  AS  ORG_BRAN_CD  ,  :BRAN_CD  AS  CHG_BRAN_CD  ,  :INSPRES_ID  AS  INSPRES_ID  FROM  GIBU.TBRA_UPSO_20140103  A  ,  (   \n";
        query +=" SELECT  NVL(MAX(CHG_SEQ),  0)  CHG_SEQ  FROM  GIBU.TBRA_BRANCHG_HISTY  )  B  WHERE  SUBSTR(A.UPSO_BD_MNG_NUM,  1,5)  =  :MNG_ZIP ";
        sobj.setSql(query);
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 변경사항 저장
    // 변경된 사항의 이력정보를 저장한다
    public DOBJ CALLbran_cd_mng_t_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("SEL9");           //변경사항 조회에서 생성시킨 OBJECT입니다.(CALLbran_cd_mng_t_SEL9)
        dvobj.Println("INS10");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_t_INS10(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS10") ;
        rvobj.Println("INS10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_t_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CHG_SEQ = dvobj.getRecord().get("CHG_SEQ");
        String   CHG_BRAN_CD = dvobj.getRecord().get("CHG_BRAN_CD");
        String   ORG_BRAN_CD = dvobj.getRecord().get("ORG_BRAN_CD");   //변경전지부
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BRANCHG_HISTY (INS_DATE, ORG_BRAN_CD, INSPRES_ID, CHG_BRAN_CD, CHG_SEQ, UPSO_CD)  \n";
        query +=" values(SYSDATE, :ORG_BRAN_CD , :INSPRES_ID , :CHG_BRAN_CD , :CHG_SEQ , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CHG_SEQ", CHG_SEQ);
        sobj.setString("CHG_BRAN_CD", CHG_BRAN_CD);
        sobj.setString("ORG_BRAN_CD", ORG_BRAN_CD);               //변경전지부
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 업소 지부변경
    // 업소의 지부를 변경한다
    public DOBJ CALLbran_cd_mng_t_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD3");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbran_cd_mng_t_XIUD3(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        rvobj.Println("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_t_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_20140103  \n";
        query +=" SET BRAN_CD = :BRAN_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE SUBSTR(UPSO_BD_MNG_NUM, 1,5) = :MNG_ZIP";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 최종 변경정보 조회
    // 최종 변경정보 조회
    public DOBJ CALLbran_cd_mng_t_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_cd_mng_t_SEL11(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.setRetcode(1);
        rvobj.Println("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_cd_mng_t_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.CHG_SEQ  ,  ROWNUM  AS  SEQ  ,  A.MNG_ZIP  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  C.DEPT_NM  ||  '('  ||  A.ORG_BRAN_CD  ||  ')'  ORG_BRAN_CD  ,  D.DEPT_NM  ||  '('  ||  A.CHG_BRAN_CD  ||  ')'  CHG_BRAN_CD  ,  DECODE(E.HAN_NM,  NULL,  A.INSPRES_ID,  E.HAN_NM||'('||A.INSPRES_ID||')')  INSPRES_ID  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  INS_DATE  FROM  GIBU.TBRA_BRANCHG_HISTY  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_DEPT  D  ,  INSA.TINS_MST01  E  WHERE  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.GIBU(+)  =  A.ORG_BRAN_CD   \n";
        query +=" AND  D.GIBU(+)  =  A.CHG_BRAN_CD   \n";
        query +=" AND  E.STAFF_NUM(+)  =  A.INSPRES_ID ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$bran_cd_mng_t
    //##**$$end
}
