
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_s03
{
    public bra05_s03()
    {
    }
    //##**$$mng_accu_attch
    /*
    */
    public DOBJ CTLmng_accu_attch(DOBJ dobj)
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
            dobj  = CALLmng_accu_attch_SEL_FILE(Conn, dobj);           //  목적지경로
            dobj  = CALLmng_accu_attch_MIUD1(Conn, dobj);           //  업소서류 첨부파일
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
    public DOBJ CTLmng_accu_attch( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_accu_attch_SEL_FILE(Conn, dobj);           //  목적지경로
        dobj  = CALLmng_accu_attch_MIUD1(Conn, dobj);           //  업소서류 첨부파일
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 목적지경로
    public DOBJ CALLmng_accu_attch_SEL_FILE(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL_FILE");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL_FILE");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_accu_attch_SEL_FILE(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL_FILE");
        rvobj.Println("SEL_FILE");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_attch_SEL_FILE(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '/upload_file/GIBU/ACCU/'  ||  SUBSTR(:ACCU_DAY,  0,  6)  AS  DFILEPATH  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        return sobj;
    }
    // 업소서류 첨부파일
    public DOBJ CALLmng_accu_attch_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_accu_attch_SEL25(Conn, dobj);           //  목적지경로와 파일명
                dobj  = CALLmng_accu_attch_FUT26( dobj);        //  파일이동
                dobj  = CALLmng_accu_attch_FUT27( dobj);        //  파일이름바꾸기
                dobj  = CALLmng_accu_attch_INS31(Conn, dobj);           //  파일업로드정보저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_accu_attch_SEL31(Conn, dobj);           //  기존파일 조회(삭제대상)
                dobj  = CALLmng_accu_attch_FUT32( dobj);        //  파일삭제
                dobj  = CALLmng_accu_attch_SEL40(Conn, dobj);           //  목적지경로와 파일명
                dobj  = CALLmng_accu_attch_FUT41( dobj);        //  파일이동
                dobj  = CALLmng_accu_attch_FUT42( dobj);        //  파일이름바꾸기
                dobj  = CALLmng_accu_attch_UPD43(Conn, dobj);           //  파일업로드정보수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_accu_attch_FUT39( dobj);        //  파일삭제
                dobj  = CALLmng_accu_attch_DEL40(Conn, dobj);           //  파일삭제
            }
        }
        return dobj;
    }
    // 파일삭제
    public DOBJ CALLmng_accu_attch_FUT39(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT39");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("FUT39");
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //소스파일명
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //소스파일경로
            record.put("SFILEPATH",SFILEPATH);
            wutil.delete(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString() );
        }
        dvobj.setName("FUT39") ;
        dvobj.Println("FUT39");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 기존파일 조회(삭제대상)
    public DOBJ CALLmng_accu_attch_SEL31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL31");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL31");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_accu_attch_SEL31(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_attch_SEL31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //관리번호
        String   FILE_TYPE = dobj.getRetObject("R").getRecord().get("FILE_TYPE");   //파일 타입
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FILE_TEMPNM  AS  SVR_FILE_NM  ,  FILE_ROUT  AS  SVR_FILE_ROUT  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  :FILE_TYPE   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("FILE_TYPE", FILE_TYPE);               //파일 타입
        return sobj;
    }
    // 목적지경로와 파일명
    public DOBJ CALLmng_accu_attch_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_accu_attch_SEL25(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        rvobj.Println("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_attch_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   FILE_TYPE = dobj.getRetObject("R").getRecord().get("FILE_TYPE");   //파일 타입
        String   FILE_NM = dobj.getRetObject("R").getRecord().get("FILE_NM");   //파일 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :ACCU_DAY  ||  '-'  ||  :ACCU_NUM  ||  '-'  ||  :ACCU_BRAN  ||  '-'  ||  :FILE_TYPE  ||  '-'  ||  NVL((SELECT  MAX(SEQ)  +  1  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  :FILE_TYPE  GROUP  BY  ACCU_DAY,  ACCU_NUM,  ACCU_BRAN),  0)  ||  '-'  ||  TO_CHAR(SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR(:FILE_NM,  INSTR(:FILE_NM,  '.',  '-1'))  AS  DFILENAME  ,  NVL((SELECT  MAX(SEQ)  +  1  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  :FILE_TYPE  GROUP  BY  ACCU_DAY,  ACCU_NUM,  ACCU_BRAN),  0)  AS  SEQ  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("FILE_TYPE", FILE_TYPE);               //파일 타입
        sobj.setString("FILE_NM", FILE_NM);               //파일 명
        return sobj;
    }
    // 파일삭제
    public DOBJ CALLmng_accu_attch_DEL40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL40");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("DEL40");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_accu_attch_DEL40(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL40") ;
        rvobj.Println("DEL40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_attch_DEL40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   FILE_TYPE = dvobj.getRecord().get("FILE_TYPE");   //파일 타입
        String   ACCU_BRAN = dvobj.getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_NUM = dvobj.getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_DAY = dvobj.getRecord().get("ACCU_DAY");   //고소 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_ACCU_ATTCH  \n";
        query +=" where ACCU_DAY=:ACCU_DAY  \n";
        query +=" and ACCU_NUM=:ACCU_NUM  \n";
        query +=" and ACCU_BRAN=:ACCU_BRAN  \n";
        query +=" and FILE_TYPE=:FILE_TYPE  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("FILE_TYPE", FILE_TYPE);               //파일 타입
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        return sobj;
    }
    // 파일삭제
    public DOBJ CALLmng_accu_attch_FUT32(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT32");
        VOBJ dvobj = dobj.getRetObject("SEL31");      //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("FUT32");
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //소스파일명
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //소스파일경로
            record.put("SFILEPATH",SFILEPATH);
            wutil.delete(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString() );
        }
        dvobj.setName("FUT32") ;
        dvobj.Println("FUT32");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 파일이동
    public DOBJ CALLmng_accu_attch_FUT26(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT26");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("FUT26");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //목적파일명
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //소스파일명
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //소스파일경로
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT26") ;
        dvobj.Println("FUT26");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 파일이름바꾸기
    public DOBJ CALLmng_accu_attch_FUT27(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT27");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("FUT27");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dobj.getRetObject("SEL25").getRecord().get("DFILENAME");         //목적파일명
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //소스파일명
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //소스파일경로
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.rename(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILENAME").toString());
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT27") ;
        dvobj.Println("FUT27");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 목적지경로와 파일명
    public DOBJ CALLmng_accu_attch_SEL40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL40");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL40");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_accu_attch_SEL40(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL40");
        rvobj.Println("SEL40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_attch_SEL40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   FILE_TYPE = dobj.getRetObject("R").getRecord().get("FILE_TYPE");   //파일 타입
        String   FILE_NM = dobj.getRetObject("R").getRecord().get("FILE_NM");   //파일 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :ACCU_DAY  ||  '-'  ||  :ACCU_NUM  ||  '-'  ||  :ACCU_BRAN  ||  '-'  ||  :FILE_TYPE  ||  '-'  ||  NVL((SELECT  MAX(SEQ)  +  1  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  :FILE_TYPE  GROUP  BY  ACCU_DAY,  ACCU_NUM,  ACCU_BRAN),  0)  ||  '-'  ||  TO_CHAR(SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR(:FILE_NM,  INSTR(:FILE_NM,  '.',  '-1'))  AS  DFILENAME  ,  NVL((SELECT  MAX(SEQ)  +  1  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  :FILE_TYPE  GROUP  BY  ACCU_DAY,  ACCU_NUM,  ACCU_BRAN),  0)  AS  SEQ  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("FILE_TYPE", FILE_TYPE);               //파일 타입
        sobj.setString("FILE_NM", FILE_NM);               //파일 명
        return sobj;
    }
    // 파일업로드정보저장
    public DOBJ CALLmng_accu_attch_INS31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS31");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS31");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_accu_attch_INS31(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS31") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_attch_INS31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FILE_NM = dvobj.getRecord().get("FILE_NM");   //파일 명
        String   FILE_TYPE = dvobj.getRecord().get("FILE_TYPE");   //파일 타입
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   FILE_ROUT = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");   //서버 파일 경로
        String   FILE_TEMPNM = dobj.getRetObject("SEL25").getRecord().get("DFILENAME");   //서버 파일 명
        double   SEQ = dobj.getRetObject("SEL25").getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_ACCU_ATTCH (ACCU_DAY, FILE_TEMPNM, FILE_ROUT, ACCU_NUM, UPSO_CD, ACCU_BRAN, FILE_TYPE, SEQ, FILE_NM)  \n";
        query +=" values(:ACCU_DAY , :FILE_TEMPNM , :FILE_ROUT , :ACCU_NUM , :UPSO_CD , :ACCU_BRAN , :FILE_TYPE , :SEQ , :FILE_NM )";
        sobj.setSql(query);
        sobj.setString("FILE_NM", FILE_NM);               //파일 명
        sobj.setString("FILE_TYPE", FILE_TYPE);               //파일 타입
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("FILE_ROUT", FILE_ROUT);               //서버 파일 경로
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //서버 파일 명
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 파일이동
    public DOBJ CALLmng_accu_attch_FUT41(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT41");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("FUT41");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //목적파일명
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //소스파일명
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //소스파일경로
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT41") ;
        dvobj.Println("FUT41");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 파일이름바꾸기
    public DOBJ CALLmng_accu_attch_FUT42(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT42");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("FUT42");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dobj.getRetObject("SEL40").getRecord().get("DFILENAME");         //목적파일명
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //소스파일명
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //소스파일경로
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.rename(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILENAME").toString());
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT42") ;
        dvobj.Println("FUT42");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 파일업로드정보수정
    public DOBJ CALLmng_accu_attch_UPD43(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD43");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_accu_attch_UPD43(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD43") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_attch_UPD43(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FILE_NM = dvobj.getRecord().get("FILE_NM");   //파일 명
        String   FILE_TYPE = dvobj.getRecord().get("FILE_TYPE");   //파일 타입
        String   ACCU_BRAN = dvobj.getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_NUM = dvobj.getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_DAY = dvobj.getRecord().get("ACCU_DAY");   //고소 일자
        String   FILE_ROUT = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");   //파일 경로
        String   FILE_TEMPNM = dobj.getRetObject("SEL40").getRecord().get("DFILENAME");   //서버 변환 파일명
        double   SEQ = dobj.getRetObject("SEL40").getRecord().getDouble("SEQ");   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_ACCU_ATTCH  \n";
        query +=" set FILE_TEMPNM=:FILE_TEMPNM , FILE_ROUT=:FILE_ROUT , SEQ=:SEQ , FILE_NM=:FILE_NM  \n";
        query +=" where ACCU_DAY=:ACCU_DAY  \n";
        query +=" and ACCU_NUM=:ACCU_NUM  \n";
        query +=" and ACCU_BRAN=:ACCU_BRAN  \n";
        query +=" and FILE_TYPE=:FILE_TYPE";
        sobj.setSql(query);
        sobj.setString("FILE_NM", FILE_NM);               //파일 명
        sobj.setString("FILE_TYPE", FILE_TYPE);               //파일 타입
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("FILE_ROUT", FILE_ROUT);               //파일 경로
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //서버 변환 파일명
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    //##**$$mng_accu_attch
    //##**$$accu_init
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/11/24
    * 설명 :
    신규 고소정보를 등록시 업소기본정보및 미납월~현재월에 해당하는 고소금액 정보를 조회한다.
    4. OSP.OSP1 : GIBU.SP_GET_DEMD_AMT()
    - 해당업소의 청구금액을 계산한다.
    5. SEL.SEL6 : 4.OSP에서 계산된 청구금액을 가지고 최종적으로 고소금액을 계산한다.
    - 고소의뢰원금: 고소기간*월정료
    - 고소의뢰가산금: 고소의뢰원금 *30%
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLaccu_init(DOBJ dobj)
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
            dobj  = CALLaccu_init_SEL2(Conn, dobj);           //  업소기본정보
            if( dobj.getRetObject("SEL2").getRecordCnt() == 0)
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="해당 업소내역이 존재하지않습니다!";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
            }
            else
            {
                dobj  = CALLaccu_init_OSP1(Conn, dobj);           //  청구 금액 생성
                dobj  = CALLaccu_init_SEL6(Conn, dobj);           //  현재기간대비금액정보
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
    public DOBJ CTLaccu_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_init_SEL2(Conn, dobj);           //  업소기본정보
        if( dobj.getRetObject("SEL2").getRecordCnt() == 0)
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="해당 업소내역이 존재하지않습니다!";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        else
        {
            dobj  = CALLaccu_init_OSP1(Conn, dobj);           //  청구 금액 생성
            dobj  = CALLaccu_init_SEL6(Conn, dobj);           //  현재기간대비금액정보
        }
        return dobj;
    }
    // 업소기본정보
    // 고소등록할 업소의 기본정보를 조회한다
    public DOBJ CALLaccu_init_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_init_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_init_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XC.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XD.  BSTYP_CD||  XD.UPSO_GRAD  GRAD  ,  XD.GRADNM  ,  XD.MONPRNCFEE  ,  XA.STAFF_CD  ,  XB.HAN_NM  STAFF_NM  ,  XA.MNGEMSTR_RESINUM  ,  XA.PERMMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.NEW_DAY  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,8)  FROM_YRMN  ,  DECODE(XA.CLSBS_YRMN,  NULL,  TO_CHAR(SYSDATE,'YYYYMM')||'01',  XA.CLSBS_YRMN||'01')  TO_YRMN  ,  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,6)  START_YRMN  ,  DECODE(XA.CLSBS_YRMN,  NULL,  TO_CHAR(SYSDATE,'YYYYMM'),  XA.CLSBS_YRMN)  END_YRMN  ,  XA.CLSBS_YRMN  ,  ''  AS  RECV_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  INSA.TINS_MST01  XB  ,  INSA.TCPM_DEPT  XC  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XD  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XD.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XA.BRAN_CD   \n";
        query +=" AND  XB.STAFF_NUM(+)  =  XA.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 청구 금액 생성
    // 해당 업소의 청구 금액를 생성하기 위한 프로시저 (GIBU.SP_GET_USE_AMT) 를 호출한다
    public DOBJ CALLaccu_init_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //업소기본정보에서 생성시킨 OBJECT입니다.(CALLaccu_init_SEL2)
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","CRET_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRET_GBN ="O";         //청구 년월
            record.put("CRET_GBN",CRET_GBN);
            ////
            String   END_YRMN = dobj.getRetObject("SEL2").getRecord().get("TO_YRMN");         //종료년월
            record.put("END_YRMN",END_YRMN);
            ////
            String   RECV_DAY = dobj.getRetObject("SEL2").getRecord().get("RECV_DAY");         //영수 일자
            record.put("RECV_DAY",RECV_DAY);
            ////
            String   START_YRMN = dobj.getRetObject("SEL2").getRecord().get("FROM_YRMN");         //시작년월
            record.put("START_YRMN",START_YRMN);
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
        String   spname ="GIBU.SP_GET_ACCU_AMT";
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
    // 현재기간대비금액정보
    public DOBJ CALLaccu_init_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_init_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_init_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   USE_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TUSE_AMT");   //사용 금액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC(:USE_AMT,  -1)  AS  CUR_ORG_AMT  ,  TRUNC(:USE_AMT  *  0.15,  -1)  AS  CUR_ADDT_AMT  ,  TRUNC(:USE_AMT  *  1.15,  -1)  AS  CUR_TOT_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("USE_AMT", USE_AMT);               //사용 금액
        return sobj;
    }
    //##**$$accu_init
    //##**$$accu_cancel_print
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/09/22
    * 설명 :
    고소취소장에 출력할 정보를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLaccu_cancel_print(DOBJ dobj)
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
            dobj  = CALLaccu_cancel_print_SEL1(Conn, dobj);           //  고소취소장출력정보
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
    public DOBJ CTLaccu_cancel_print( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_cancel_print_SEL1(Conn, dobj);           //  고소취소장출력정보
        return dobj;
    }
    // 고소취소장출력정보
    public DOBJ CALLaccu_cancel_print_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_cancel_print_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_cancel_print_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_GBN = dobj.getRetObject("S").getRecord().get("ACCU_GBN");   //고소 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.HAN_NM  STAFF_NM  ,  DECODE(A.ACCU_OBJ,  'B',  E.MNGEMSTR_NM,  E.PERMMSTR_NM)  NAME  ,  A.ACCU_DAY  ,  DECODE(A.ACCU_GBN,  'A',  C.BIGO,  D.CODE_NM)  PLCST_NM  ,  TO_CHAR(SYSDATE,  'YYYYMMDD')  PRINT_DATE  ,  E.UPSO_NM  FROM  GIBU.TBRA_ACCU  A  ,  INSA.TINS_MST01  B  ,  FIDU.TENV_CODE  C  ,  FIDU.TENV_CODE  D  ,  GIBU.TBRA_UPSO  E  WHERE  A.ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  A.ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  A.ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  A.ACCU_GBN  =  :ACCU_GBN   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.STAFF_NUM(+)  =  A.STAFF_CD   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00214'   \n";
        query +=" AND  C.CODE_CD(+)  =  A.PLCST_CD   \n";
        query +=" AND  D.HIGH_CD(+)  =  '00219'   \n";
        query +=" AND  D.CODE_CD(+)  =  A.PLCST_CD   \n";
        query +=" AND  E.UPSO_CD  =  A.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_GBN", ACCU_GBN);               //고소 구분
        return sobj;
    }
    //##**$$accu_cancel_print
    //##**$$accu_detail
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/12/04
    * 설명 :
    등록된 고소의 상세정보를 조회한다.
    1. SEL.SEL11
    1) GIBU.FT_GET_START_REPT_YRMN(XA.UPSO_CD,8)
    - 해당 업소의 시작 납입 년월을 계산한다.
    - 납입 내역이 없는 경우 개업년월이 시작년월이 된다.
    - 8 : 결과 년월의 자릿수를 나타낸다.
    5.SEL.SEL10 : 폐업된 업소가 아닌경우 미납월~현재월 기준의 고소금액 정보를 구한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLaccu_detail(DOBJ dobj)
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
            dobj  = CALLaccu_detail_SEL1(Conn, dobj);           //  업소기본정보
            dobj  = CALLaccu_detail_SEL2(Conn, dobj);           //  고소정보
            dobj  = CALLaccu_detail_SEL3(Conn, dobj);           //  고소메모정보
            if( dobj.getRetObject("SEL1").getRecord().get("CLSBS_YRMN").equals(""))
            {
                dobj  = CALLaccu_detail_OSP6(Conn, dobj);           //  고소 금액 생성
                if(dobj.getRetObject("S").getRecord().getInt("ACCU_DAY") < 20161101 && !dobj.getRetObject("S").getRecord().get("ACCU_DAY").equals(""))
                {
                    dobj  = CALLaccu_detail_SEL8(Conn, dobj);           //  현재기간대비금액정보
                    dobj  = CALLaccu_detail_MRG9( dobj);        //  MRG
                }
                else
                {
                    dobj  = CALLaccu_detail_SEL10(Conn, dobj);           //  현재기간대비금액정보
                    dobj  = CALLaccu_detail_MRG9( dobj);        //  MRG
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
    public DOBJ CTLaccu_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_detail_SEL1(Conn, dobj);           //  업소기본정보
        dobj  = CALLaccu_detail_SEL2(Conn, dobj);           //  고소정보
        dobj  = CALLaccu_detail_SEL3(Conn, dobj);           //  고소메모정보
        if( dobj.getRetObject("SEL1").getRecord().get("CLSBS_YRMN").equals(""))
        {
            dobj  = CALLaccu_detail_OSP6(Conn, dobj);           //  고소 금액 생성
            if(dobj.getRetObject("S").getRecord().getInt("ACCU_DAY") < 20161101 && !dobj.getRetObject("S").getRecord().get("ACCU_DAY").equals(""))
            {
                dobj  = CALLaccu_detail_SEL8(Conn, dobj);           //  현재기간대비금액정보
                dobj  = CALLaccu_detail_MRG9( dobj);        //  MRG
            }
            else
            {
                dobj  = CALLaccu_detail_SEL10(Conn, dobj);           //  현재기간대비금액정보
                dobj  = CALLaccu_detail_MRG9( dobj);        //  MRG
            }
        }
        return dobj;
    }
    // 업소기본정보
    // 고소정보가 등록된 업소의 기본정보와 현재시점의 고소기간 정보를 조회한다
    public DOBJ CALLaccu_detail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_detail_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_detail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ACCU_GBN = dobj.getRetObject("S").getRecord().get("ACCU_GBN");   //고소 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XE.BSTYP_CD||XE.UPSO_GRAD  GRAD  ,  XE.GRADNM  ,  XE.MONPRNCFEE  ,  XA.STAFF_CD  ,  XC.HAN_NM  STAFF_NM  ,  XA.MNGEMSTR_RESINUM  ,  XA.PERMMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.NEW_DAY  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  GIBU.FT_GET_START_REPT_YRMN(XA.UPSO_CD,8)  FROM_YRMN  ,  DECODE(XA.CLSBS_YRMN,  NULL,  TO_CHAR(SYSDATE,'YYYYMM'),  XA.CLSBS_YRMN)||'01'  TO_YRMN  ,  XA.CLSBS_YRMN  ,  ''  AS  RECV_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_ACCU  XB  ,  INSA.TINS_MST01  XC  ,  INSA.TCPM_DEPT  XD  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XE  ,  (   \n";
        query +=" SELECT  DECODE(TB.REPT_YRMN,  NULL,  SUBSTR(TA.OPBI_DAY,1,6),  TB.REPT_YRMN)  REPT_YRMN  ,  TA.UPSO_CD  FROM  GIBU.TBRA_UPSO  TA  ,(   \n";
        query +=" SELECT  MAX(A.NOTE_YRMN)  REPT_YRMN  ,  A.UPSO_CD  FROM  GIBU.TBRA_NOTE  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )TB  WHERE  TA.UPSO_CD  =  TB.UPSO_CD(+)  )  XF  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  XB.ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  XB.ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  XB.ACCU_GBN  =  :ACCU_GBN   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XA.BRAN_CD   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XF.UPSO_CD(+)  =  XA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ACCU_GBN", ACCU_GBN);               //고소 구분
        return sobj;
    }
    // 고소정보
    // 고소 상세 정보를 조회한다
    public DOBJ CALLaccu_detail_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_detail_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_detail_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ACCU_GBN = dobj.getRetObject("S").getRecord().get("ACCU_GBN");   //고소 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.ACCU_DAY  ,  A.ACCU_NUM  ,  A.ACCU_BRAN  ,  A.UPSO_CD  ,  A.ACCU_GBN  ,  ACCU_OBJ  ,  A.PLCST_CD  ,  DECODE(ACCU_GBN,  'A',  D.BIGO,  E.CODE_NM)  PLCST_NM  ,  A.EVT_NUM  ,  A.EVT_NUM2  ,  A.INSPECTOR  ,  A.REQ_DAY  ,  A.JUDG_CD  ,  C.CODE_NM  JUDG_NM  ,  A.JUDG_BRE  ,  DECODE(A.START_YRMN,'','',A.START_YRMN  ||'01')  START_YRMN  ,  DECODE(A.END_YRMN,'','',A.END_YRMN  ||'01')  END_YRMN  ,  NVL(A.REQ_ORG_AMT,0)  +  NVL(A.REQ_ADDT_AMT,0)  REQ_TOT_AMT  ,  A.REQ_ORG_AMT  ,  A.REQ_ADDT_AMT  ,  A.COMPN_DAY  ,  A.COMPN_DAY2  ,  A.REPT_GBN  ,  DECODE(A.SOL_START_YRMN,'','',A.SOL_START_YRMN  ||'01')  SOL_START_YRMN  ,  DECODE(A.SOL_END_YRMN,'','',A.SOL_END_YRMN  ||'01')  SOL_END_YRMN  ,  NVL(A.SOL_ORG_AMT,0)  +  NVL(A.SOL_ADDT_AMT,0)  SOL_TOT_AMT  ,  A.SOL_ORG_AMT  ,  A.SOL_ADDT_AMT  ,  A.COMIS  ,  B.STAFF_CD  ,  F.SEQ  ,  F.FILE_NM  ,  F.FILE_ROUT  ,  F.FILE_TEMPNM  ,  A.PROD_CD  ,   \n";
        query +=" (SELECT  PROD_TTL  FROM  FIDU.TOPU_PROD  WHERE  PROD_CD  =  A.PROD_CD)  AS  PROD_NM  FROM  GIBU.TBRA_ACCU  A  ,  GIBU.TBRA_UPSO  B  ,  FIDU.TENV_CODE  C  ,  FIDU.TENV_CODE  D  ,  FIDU.TENV_CODE  E  ,  (   \n";
        query +=" SELECT  ACCU_DAY,  ACCU_NUM,  ACCU_BRAN,  UPSO_CD,  SEQ,  FILE_NM,  FILE_ROUT,  FILE_TYPE,  FILE_TEMPNM,  FILE_SIZE,  FILES  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  SEQ  =  (   \n";
        query +=" SELECT  MAX(SEQ)  AS  SEQ  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN  )  )  F  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  A.ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  A.ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  A.ACCU_GBN  =  :ACCU_GBN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00220'   \n";
        query +=" AND  C.CODE_CD(+)  =  A.JUDG_CD   \n";
        query +=" AND  D.HIGH_CD(+)  =  '00214'   \n";
        query +=" AND  D.CODE_CD(+)  =  A.PLCST_CD   \n";
        query +=" AND  E.HIGH_CD(+)  =  '00219'   \n";
        query +=" AND  E.CODE_CD(+)  =  A.PLCST_CD   \n";
        query +=" AND  A.ACCU_DAY  =  F.ACCU_DAY(+)   \n";
        query +=" AND  A.ACCU_NUM  =  F.ACCU_NUM(+)   \n";
        query +=" AND  A.ACCU_BRAN  =  F.ACCU_BRAN(+) ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ACCU_GBN", ACCU_GBN);               //고소 구분
        return sobj;
    }
    // 고소메모정보
    public DOBJ CALLaccu_detail_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_detail_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_detail_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  MEMO_DAY  ,  MEMO_NUM  ,  MEMO  ,  SNUM  FROM  GIBU.TBRA_ACCU_MEMO  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 고소 금액 생성
    public DOBJ CALLaccu_detail_OSP6(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP6");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL1");         //업소기본정보에서 생성시킨 OBJECT입니다.(CALLaccu_detail_SEL1)
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","CRET_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRET_GBN ="O";         //청구 년월
            record.put("CRET_GBN",CRET_GBN);
            ////
            String   END_YRMN = dobj.getRetObject("SEL1").getRecord().get("TO_YRMN");         //종료년월
            record.put("END_YRMN",END_YRMN);
            ////
            String   RECV_DAY = dobj.getRetObject("SEL1").getRecord().get("TO_YRMN");         //영수 일자
            record.put("RECV_DAY",RECV_DAY);
            ////
            String   START_YRMN = dobj.getRetObject("SEL1").getRecord().get("FROM_YRMN");         //시작년월
            record.put("START_YRMN",START_YRMN);
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
        String   spname ="GIBU.SP_GET_ACCU_AMT";
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
        robj.setName("OSP6");
        robj.Println("OSP6");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 현재기간대비금액정보
    public DOBJ CALLaccu_detail_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_detail_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.setRetcode(1);
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_detail_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   USE_AMT = dobj.getRetObject("OSP6").getRecord().getDouble("P_TUSE_AMT");   //사용 금액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC(:USE_AMT,  -1)  AS  CUR_ORG_AMT  ,  TRUNC(:USE_AMT  *  0.3,  -1)  AS  CUR_ADDT_AMT  ,  TRUNC(:USE_AMT  *  1.3,  -1)  AS  CUR_TOT_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("USE_AMT", USE_AMT);               //사용 금액
        return sobj;
    }
    // MRG
    public DOBJ CALLaccu_detail_MRG9(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG9");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL10, SEL8","CUR_ORG_AMT, CUR_ADDT_AMT, CUR_TOT_AMT");
        rvobj.setName("MRG9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 현재기간대비금액정보
    public DOBJ CALLaccu_detail_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_detail_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.setRetcode(1);
        rvobj.Println("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_detail_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   USE_AMT = dobj.getRetObject("OSP6").getRecord().getDouble("P_TUSE_AMT");   //사용 금액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC(:USE_AMT,  -1)  AS  CUR_ORG_AMT  ,  TRUNC(:USE_AMT  *  0.15,  -1)  AS  CUR_ADDT_AMT  ,  TRUNC(:USE_AMT  *  1.15,  -1)  AS  CUR_TOT_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("USE_AMT", USE_AMT);               //사용 금액
        return sobj;
    }
    //##**$$accu_detail
    //##**$$accu_cancel_print2
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/09/25
    * 설명 :
    진술서 출력할 데이타를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLaccu_cancel_print2(DOBJ dobj)
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
            dobj  = CALLaccu_cancel_print2_SEL1(Conn, dobj);           //  진술서출력
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
    public DOBJ CTLaccu_cancel_print2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_cancel_print2_SEL1(Conn, dobj);           //  진술서출력
        return dobj;
    }
    // 진술서출력
    public DOBJ CALLaccu_cancel_print2_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_cancel_print2_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_cancel_print2_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_GBN = dobj.getRetObject("S").getRecord().get("ACCU_GBN");   //고소 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.HAN_NM  STAFF_NM  ,  DECODE(SUBSTR(XB.RESIINS_NUM,7,1),'1','男','女')  S_GBN  ,  EXTRACT(YEAR  FROM  SYSDATE)  -  (DECODE(SUBSTR(XB.RESIINS_NUM,7,1),'1',  '19','2','19','20')  ||SUBSTR(XB.RESIINS_NUM,1,2))  +1  AGE  ,  DECODE(SUBSTR(XB.RESIINS_NUM,7,1),'1',  '19','2','19','20')||SUBSTR(XB.RESIINS_NUM,1,6)  BIRTH_DAY  ,  XB.RESIINS_NUM  ,  XD.ADDR||'  '||XD.HNM  BRAN_ADDR  ,  XD.PHON_NUM  ,  XD.FAX_NUM  ,  XC.CP_NUM  ,  XD.BIPLC_NM  BRAN_NM  ,  XA.ACCU_DAY  ,  XF.UPSO_NEW_ADDR1  ||  DECODE(XF.UPSO_NEW_ADDR2,  '',  '',  ',  '||XF.UPSO_NEW_ADDR2)  ||  XF.UPSO_REF_INFO  UPSO_ADDR  ,  XF.UPSO_NM  ,  XG.GRADNM  ,  DECODE(XA.ACCU_OBJ,  'B',  XF.MNGEMSTR_NM,  XF.PERMMSTR_NM)  NAME  ,  XA.COMPN_DAY2  AS  COMPN_DAY  ,  TO_CHAR(SYSDATE,  'YYYYMMDD')  PRINT_DAY  ,  DECODE(XA.ACCU_GBN,  'A',  XE.BIGO,  XH.CODE_NM)  PLCST_NM  ,  XA.INSPECTOR  ,  XD.POST_NUM  FROM  GIBU.TBRA_ACCU  XA  ,  INSA.TINS_MST01  XB  ,  INSA.TINS_MST02  XC  ,  INSA.TCPM_BIPLC  XD  ,  FIDU.TENV_CODE  XE  ,  GIBU.TBRA_UPSO  XF  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZC.GRADNM  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  'z'   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.BSTYP_CD  )  XG  ,  FIDU.TENV_CODE  XH  WHERE  XA.ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  XA.ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  XA.ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  XA.ACCU_GBN  =  :ACCU_GBN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XE.HIGH_CD(+)  =  '00214'   \n";
        query +=" AND  XE.CODE_CD(+)  =  XA.PLCST_CD   \n";
        query +=" AND  XH.HIGH_CD(+)  =  '00219'   \n";
        query +=" AND  XH.CODE_CD(+)  =  XA.PLCST_CD   \n";
        query +=" AND  XC.RESIINS_NUM  =  XB.RESIINS_NUM   \n";
        query +=" AND  XF.BRAN_CD  =  XD.GIBU   \n";
        query +=" AND  XF.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XG.UPSO_CD(+)  =  XA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_GBN", ACCU_GBN);               //고소 구분
        return sobj;
    }
    //##**$$accu_cancel_print2
    //##**$$p_plcst_judg
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/10/01
    * 설명 :
    경찰서, 법원, 판결내용을 조회한다.
    CODE_GBN : P- 경찰서 정보          (FIDU.TENV_CODE 테이블의 HIGH_CD = '00214' 검색)
    CODE_GBN : L - 법원정보             (FIDU.TENV_CODE 테이블의 HIGH_CD = '00219' 검색)
    CODE_GBN : 그외 - 판결코드 정보  (FIDU.TENV_CODE 테이블의 HIGT_CD = '00220' 검색)
    1.NLL.NLL3 : 프로세스빌더에서는 S 에서 바로 분기(조건달기)가 가능하지 않기 때문에 조건에 따라 분기가능하도록 넣음
    4.MRG.MRG5 : 각각 조건별로 조회된 데이타중 화면에 보여질 최종데이타를 MIFLATFORM에 전달한다
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLp_plcst_judg(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("CODE_GBN").equals("P"))
            {
                dobj  = CALLp_plcst_judg_SEL1(Conn, dobj);           //  경찰서코드리스트
                dobj  = CALLp_plcst_judg_MRG5( dobj);        //  결과취합
            }
            else if( dobj.getRetObject("S").getRecord().get("CODE_GBN").equals("L"))
            {
                dobj  = CALLp_plcst_judg_SEL7(Conn, dobj);           //  법원코드리스트
                dobj  = CALLp_plcst_judg_MRG5( dobj);        //  결과취합
            }
            else
            {
                dobj  = CALLp_plcst_judg_SEL2(Conn, dobj);           //  판결코드리스트
                dobj  = CALLp_plcst_judg_MRG5( dobj);        //  결과취합
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
    public DOBJ CTLp_plcst_judg( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("CODE_GBN").equals("P"))
        {
            dobj  = CALLp_plcst_judg_SEL1(Conn, dobj);           //  경찰서코드리스트
            dobj  = CALLp_plcst_judg_MRG5( dobj);        //  결과취합
        }
        else if( dobj.getRetObject("S").getRecord().get("CODE_GBN").equals("L"))
        {
            dobj  = CALLp_plcst_judg_SEL7(Conn, dobj);           //  법원코드리스트
            dobj  = CALLp_plcst_judg_MRG5( dobj);        //  결과취합
        }
        else
        {
            dobj  = CALLp_plcst_judg_SEL2(Conn, dobj);           //  판결코드리스트
            dobj  = CALLp_plcst_judg_MRG5( dobj);        //  결과취합
        }
        return dobj;
    }
    // 경찰서코드리스트
    // FIDU.TENV_CODE 테이블의 HIGH_CD='00214' 검색
    public DOBJ CALLp_plcst_judg_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_plcst_judg_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_plcst_judg_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CODE_CD = dobj.getRetObject("S").getRecord().get("CODE_CD");   //공통코드
        String   CODE_NM = dobj.getRetObject("S").getRecord().get("CODE_NM");   //코드명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CODE_CD  ,  BIGO  AS  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00214'   \n";
        query +=" AND  CODE_CD  =  DECODE(:CODE_CD,  NULL,  CODE_CD,  :CODE_CD)   \n";
        query +=" AND  BIGO  LIKE  '%'||:CODE_NM||'%'   \n";
        query +=" AND  USE_YN  =  'Y' ";
        sobj.setSql(query);
        sobj.setString("CODE_CD", CODE_CD);               //공통코드
        sobj.setString("CODE_NM", CODE_NM);               //코드명
        return sobj;
    }
    // 결과취합
    public DOBJ CALLp_plcst_judg_MRG5(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG5");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL7, SEL1, SEL2","");
        rvobj.setName("MRG5") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 법원코드리스트
    // FIDU.TENV_CODE 테이블의 HIGH_CD = '00219' 검색
    public DOBJ CALLp_plcst_judg_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_plcst_judg_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_plcst_judg_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CODE_CD = dobj.getRetObject("S").getRecord().get("CODE_CD");   //공통코드
        String   CODE_NM = dobj.getRetObject("S").getRecord().get("CODE_NM");   //코드명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CODE_CD  ,  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00219'   \n";
        query +=" AND  CODE_CD  =  DECODE(:CODE_CD,  NULL,  CODE_CD,  :CODE_CD)   \n";
        query +=" AND  CODE_NM  LIKE  '%'||:CODE_NM||'%'   \n";
        query +=" AND  USE_YN  =  'Y' ";
        sobj.setSql(query);
        sobj.setString("CODE_CD", CODE_CD);               //공통코드
        sobj.setString("CODE_NM", CODE_NM);               //코드명
        return sobj;
    }
    // 판결코드리스트
    // FIDU.TENV_CODE 테이블에서 정보를 가져온다. HIGT_CD='00220'
    public DOBJ CALLp_plcst_judg_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_plcst_judg_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_plcst_judg_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CODE_CD  ,  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00220'   \n";
        query +=" AND  USE_YN  =  'Y'  ORDER  BY  SORT_CD ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$p_plcst_judg
    //##**$$bran_rept_end
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/10/07
    * 설명 :
    고소날짜(ACCU_DAY) 를 기준으로 해당 지부의 입금마감이 됐는지 체크한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbran_rept_end(DOBJ dobj)
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
            dobj  = CALLbran_rept_end_SEL1(Conn, dobj);           //  지부별마감체크
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
    public DOBJ CTLbran_rept_end( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbran_rept_end_SEL1(Conn, dobj);           //  지부별마감체크
        return dobj;
    }
    // 지부별마감체크
    // 지부별 마감테이블의 해당 년월 정보가 있는지 체크한다
    public DOBJ CALLbran_rept_end_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_rept_end_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_rept_end_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(:ACCU_DAY,1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(:ACCU_DAY,5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$bran_rept_end
    //##**$$use_amt
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/11/24
    * 수정일 : 2012/04/04 (요청자:김현이)
    자동이체업소 할인 비적용, 더 넣은 금액 계산은 고소의뢰원금 계산의 마지막 단계에 진행(전체합에서 빼자)
    * 설명 :
    고소기간에 따른 고소금액을 계산한다.
    1. OSP.OSP1 : GIBU.SP_GET_ACCU_AMT()
    - 해당업소의 청구금액을 계산한다.
    - GIBU.SP_GET_ACCU_AMT()를 수정하여 만들었다.
    2. SEL.SEL2 : 1.OSP에서 계산된 청구금액을 가지고 최종적으로 고소금액을 계산한다.
    - 고소의뢰원금: 고소기간의 월정료 * 미납개월 - 더넣은금액
    (오프라인 할인은 적용, 자동이체할인은 비적용)
    (더 넣은 금액의 계산은 사용료 계산과 다르게 고소의뢰원금계산의 마지막 단계에 진행한다.
    - 고소의뢰가산금: 고소의뢰원금 *30%
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLuse_amt(DOBJ dobj)
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
            dobj  = CALLuse_amt_SEL3(Conn, dobj);           //  RECV_DAY 셋팅
            dobj  = CALLuse_amt_OSP1(Conn, dobj);           //  청구 금액 생성
            if(dobj.getRetObject("S").getRecord().getInt("ACCU_DAY") < 20161101 && !dobj.getRetObject("S").getRecord().get("ACCU_DAY").equals(""))
            {
                dobj  = CALLuse_amt_SEL5(Conn, dobj);           //  최종고소의뢰원금,가산금
                dobj  = CALLuse_amt_MRG7( dobj);        //  머지
            }
            else
            {
                dobj  = CALLuse_amt_SEL2(Conn, dobj);           //  최종고소의뢰원금,가산금
                dobj  = CALLuse_amt_MRG7( dobj);        //  머지
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
    public DOBJ CTLuse_amt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLuse_amt_SEL3(Conn, dobj);           //  RECV_DAY 셋팅
        dobj  = CALLuse_amt_OSP1(Conn, dobj);           //  청구 금액 생성
        if(dobj.getRetObject("S").getRecord().getInt("ACCU_DAY") < 20161101 && !dobj.getRetObject("S").getRecord().get("ACCU_DAY").equals(""))
        {
            dobj  = CALLuse_amt_SEL5(Conn, dobj);           //  최종고소의뢰원금,가산금
            dobj  = CALLuse_amt_MRG7( dobj);        //  머지
        }
        else
        {
            dobj  = CALLuse_amt_SEL2(Conn, dobj);           //  최종고소의뢰원금,가산금
            dobj  = CALLuse_amt_MRG7( dobj);        //  머지
        }
        return dobj;
    }
    // RECV_DAY 셋팅
    //""값을 바로 넘기면 에러가 나서 중간단계를 둠
    public DOBJ CALLuse_amt_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLuse_amt_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLuse_amt_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  AS  RECV_DAY  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 청구 금액 생성
    // 해당 업소의 청구 금액를 생성하기 위한 프로시저 (GIBU.SP_GET_USE_AMT) 를 호출한다
    public DOBJ CALLuse_amt_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","CRET_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRET_GBN ="O";         //청구 년월
            record.put("CRET_GBN",CRET_GBN);
            ////
            String   RECV_DAY = dobj.getRetObject("SEL3").getRecord().get("RECV_DAY");         //영수 일자
            record.put("RECV_DAY",RECV_DAY);
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
        String   spname ="GIBU.SP_GET_ACCU_AMT";
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
    // 최종고소의뢰원금,가산금
    public DOBJ CALLuse_amt_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLuse_amt_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.setRetcode(1);
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLuse_amt_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   USE_AMT = dobj.getRetObject("OSP6").getRecord().getDouble("P_TUSE_AMT");   //사용 금액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC(:USE_AMT,  -1)  AS  REQ_ORG_AMT  ,  TRUNC(:USE_AMT  *  0.3,  -1)  AS  REQ_ADDT_AMT  ,  TRUNC(:USE_AMT  *  1.3,  -1)  AS  REQ_TOT_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("USE_AMT", USE_AMT);               //사용 금액
        return sobj;
    }
    // 머지
    public DOBJ CALLuse_amt_MRG7(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG7");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL2, SEL5","REQ_ORG_AMT, REQ_ADDT_AMT, REQ_TOT_AMT");
        rvobj.setName("MRG7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 최종고소의뢰원금,가산금
    public DOBJ CALLuse_amt_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLuse_amt_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLuse_amt_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   USE_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TUSE_AMT");   //사용 금액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC(:USE_AMT,  -1)  AS  REQ_ORG_AMT  ,  TRUNC(:USE_AMT  *  0.15,  -1)  AS  REQ_ADDT_AMT  ,  TRUNC(:USE_AMT  *  1.15,  -1)  AS  REQ_TOT_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("USE_AMT", USE_AMT);               //사용 금액
        return sobj;
    }
    //##**$$use_amt
    //##**$$accu_mng
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/11/03
    * 설명 :
    업소별 고소정보를 등록한다.
    삭제기능 없음 (삭제하고자 할경우에는 전산실에 의뢰해서 수동으로 지우기로 함)
    DATASET S:  : 고소정보
    DATASET S1 : 고소메모정보
    1. MIUD.MIUD1 : DATASET S를 처리한다.
    2. MIUD.MIUD33 : DATASET S1을 처리한다.
    15.SEL.SEL46 : 해당업소의 입금정보중 고소로 입금된 가장 최근 정보를 조회한다.
    입금정보가 없을 경우에는 22.RBK.RBK48의 메시지를 보여준다.
    입금정보가 있을 경우에는 해당 입금정보로 고소해결이 이미 등록됐는지 체크한다.(16.SEL.SEL47 )
    16.SEL.SEL47 : 입금정보로 고소해결이 이미 등록됐는지 체크한다.
    이미 고소해결로 등록된 입금정보일 경우  22.RBK.RBK48의 메시지를 보여준다.
    사용할수 있는 입금정보일 경우 고소정보에 등록시킨다.
    20.GLV.GLV43 : 새로 생성된 고소번호(ACCU_NUM)의 정보를 저장하고 있는 변수
    고소메모 등록&고소등록 결과 조회시 사용한다.
    * 수정일1: 2009.12.15
    * 수정자 :
    * 수정내용 :
    - 판결코드가 "1(입금취하)" 인 경우에는 고소해결로 입금내역이 있을 경우에만 저장이 된다.
    입금내역이 없을 경우에는 입금정보 먼저 저장후 고소해결 정보를 수정할수 있다.
    */
    public DOBJ CTLaccu_mng(DOBJ dobj)
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
            dobj  = CALLaccu_mng_MIUD1(Conn, dobj);           //  고소관리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLaccu_mng_MIUD33(Conn, dobj);           //  고소메모관리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLaccu_mng_SEL42(Conn, dobj);           //  고소정보
            dobj  = CALLaccu_mng_SEL45(Conn, dobj);           //  고소메모정보
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
    public DOBJ CTLaccu_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_mng_MIUD1(Conn, dobj);           //  고소관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLaccu_mng_MIUD33(Conn, dobj);           //  고소메모관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLaccu_mng_SEL42(Conn, dobj);           //  고소정보
        dobj  = CALLaccu_mng_SEL45(Conn, dobj);           //  고소메모정보
        return dobj;
    }
    // 고소관리
    public DOBJ CALLaccu_mng_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MIUD1");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLaccu_mng_SEL43(Conn, dobj);           //  조건체크
                if( dobj.getRetObject("R").getRecord().get("JUDG_CD").equals("1"))
                {
                    dobj  = CALLaccu_mng_SEL46(Conn, dobj);           //  입금정보조회
                    if( dobj.getRetObject("SEL46").getRecordCnt() > 0)
                    {
                        dobj  = CALLaccu_mng_SEL47(Conn, dobj);           //  중복입금처리방지
                        if( dobj.getRetObject("SEL47").getRecordCnt() > 0 && dobj.getRetObject("SEL46").getRecord().get("DISTR_GBN").equals(""))
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="입금내역 정보가 없습니다. 입금정보를 확인해 주세요.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                        else
                        {
                            dobj  = CALLaccu_mng_SEL16(Conn, dobj);           //  고소번호생성
                            dobj  = CALLaccu_mng_INS17(Conn, dobj);           //  고소등록
                            ////
                            String   ACCU_NUM = dobj.getRetObject("SEL16").getRecord().get("ACCU_NUM");         //고소 번호
                            dobj.setGVValue("ACCU_NUM",ACCU_NUM);
                        }
                    }
                    else
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="입금내역 정보가 없습니다. 입금정보를 확인해 주세요.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                }
                else
                {
                    dobj  = CALLaccu_mng_SEL16(Conn, dobj);           //  고소번호생성
                    dobj  = CALLaccu_mng_INS17(Conn, dobj);           //  고소등록
                    ////
                    String   ACCU_NUM = dobj.getRetObject("SEL16").getRecord().get("ACCU_NUM");         //고소 번호
                    dobj.setGVValue("ACCU_NUM",ACCU_NUM);
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLaccu_mng_SEL50(Conn, dobj);           //  완료일자 체크
                if(!dobj.getRetObject("SEL50").getRecord().get("COMPN_DAY").equals(""))
                {
                    dobj  = CALLaccu_mng_SEL40(Conn, dobj);           //  마감정보확인
                    if( dobj.getRetObject("SEL40").getRecord().getDouble("CNT") > 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="해당기간 중 청구 또는 입금 마감내역이 존재합니다.마감정보를 확인해 주세요.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else
                    {
                        dobj  = CALLaccu_mng_SEL49(Conn, dobj);           //  조건체크
                        dobj  = CALLaccu_mng_UPD37(Conn, dobj);           //  고소정보수정
                    }
                }
                else
                {
                    dobj  = CALLaccu_mng_SEL49(Conn, dobj);           //  조건체크
                    dobj  = CALLaccu_mng_UPD37(Conn, dobj);           //  고소정보수정
                }
            }
        }
        return dobj;
    }
    // 조건체크
    // MIUD 타입일경우 조건이 바로 올수 없어 빈 SELECT 문장을 중간에 넣음
    public DOBJ CALLaccu_mng_SEL43(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL43");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL43");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_mng_SEL43(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL43");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_SEL43(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  BLK  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 완료일자 체크
    public DOBJ CALLaccu_mng_SEL50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL50");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL50");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_mng_SEL50(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL50");
        rvobj.Println("SEL50");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_SEL50(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   COMPN_DAY = dobj.getRetObject("R").getRecord().get("COMPN_DAY");   //완결 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :COMPN_DAY  AS  COMPN_DAY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("COMPN_DAY", COMPN_DAY);               //완결 일자
        return sobj;
    }
    // 입금정보조회
    // 입금취하일 경우 입금정보가 먼저 등록되어 있어야 저장이 된다
    public DOBJ CALLaccu_mng_SEL46(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL46");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL46");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_mng_SEL46(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL46");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_SEL46(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_GBN  ,  B.DISTR_SEQ  FROM  GIBU.TBRA_REPT  A  ,  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  NOTE_YRMN  =  (   \n";
        query +=" SELECT  MAX(NOTE_YRMN)  FROM  GIBU.TBRA_NOTE  A  WHERE  UPSO_CD  =:UPSO_CD   \n";
        query +=" AND  ACCU_GBN  IN  ('06','22')  )   \n";
        query +=" AND  NOTE_NUM  =  '0001'   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD  )  B  WHERE  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  B.REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 마감정보확인
    // 완결일자(COMPN_DAY)를 이용해서 마감정보를 확인한다
    public DOBJ CALLaccu_mng_SEL40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL40");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL40");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_mng_SEL40(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL40");
        rvobj.Println("SEL40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_SEL40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MON ="";        //휴일월
        String YEAR ="";        //검색년도
        String   MON_1 = dobj.getRetObject("R").getRecord().get("COMPN_DAY");   //휴일월
        String   YEAR_1 = dobj.getRetObject("R").getRecord().get("COMPN_DAY");   //검색년도
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  END_YEAR  =  SUBSTR(:YEAR_1,1,4)   \n";
        query +=" AND  END_MON  =  SUBSTR(:MON_1,5,2) ";
        sobj.setSql(query);
        sobj.setString("MON_1", MON_1);               //휴일월
        sobj.setString("YEAR_1", YEAR_1);               //검색년도
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 중복입금처리방지
    // 기존 고소 테이블에 해당 입금 내용으로 등록이 되어 있는지 확인한다
    public DOBJ CALLaccu_mng_SEL47(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL47");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL47");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_mng_SEL47(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL47");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_SEL47(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL46").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL46").getRecord().get("REPT_NUM");   //입금 번호
        String   REPT_GBN = dobj.getRetObject("SEL46").getRecord().get("REPT_GBN");   //입금 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_ACCU  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        return sobj;
    }
    // 고소번호생성
    public DOBJ CALLaccu_mng_SEL16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL16");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL16");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_mng_SEL16(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL16");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_SEL16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("R").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_BRAN = dobj.getRetObject("R").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(ACCU_NUM),  0)  +  1,  4,  '0')  ACCU_NUM  FROM  GIBU.TBRA_ACCU  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 조건체크
    // MIUD 타입일경우 조건이 바로 올수 없어 의미없는 SELECT 문장을 중간에 넣음
    public DOBJ CALLaccu_mng_SEL49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL49");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL49");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_mng_SEL49(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL49");
        rvobj.Println("SEL49");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_SEL49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  BLK  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 고소등록
    public DOBJ CALLaccu_mng_INS17(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLaccu_mng_INS17(dobj, dvobj);
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
    private SQLObject SQLaccu_mng_INS17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //종료년월
        String INS_DATE ="";        //등록 일시
        String REQ_DAY ="";        //의뢰 일자
        String SOL_END_YRMN ="";        //해결기간 종료년월
        String SOL_START_YRMN ="";        //해결기간 시작년월
        String START_YRMN ="";        //시작년월
        String   EVT_NUM2 = dvobj.getRecord().get("EVT_NUM2");
        String   JUDG_CD = dvobj.getRecord().get("JUDG_CD");   //판결 코드
        String   SOL_START_YRMN_1 = dvobj.getRecord().get("SOL_START_YRMN");   //해결기간 시작년월
        String   ACCU_OBJ = dvobj.getRecord().get("ACCU_OBJ");   //고소 대상
        double   SOL_ORG_AMT = dvobj.getRecord().getDouble("SOL_ORG_AMT");   //해결 원금
        String   START_YRMN_1 = dvobj.getRecord().get("START_YRMN");   //시작년월
        double   SOL_ADDT_AMT = dvobj.getRecord().getDouble("SOL_ADDT_AMT");   //해결 가산금
        String   ACCU_GBN = dvobj.getRecord().get("ACCU_GBN");   //고소 구분
        String   ACCU_BRAN = dvobj.getRecord().get("ACCU_BRAN");   //고소 지부
        double   REQ_ADDT_AMT = dvobj.getRecord().getDouble("REQ_ADDT_AMT");   //의뢰 가산금액
        String   ACCU_DAY = dvobj.getRecord().get("ACCU_DAY");   //고소 일자
        String   END_YRMN_1 = dvobj.getRecord().get("END_YRMN");   //종료년월
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   SOL_END_YRMN_1 = dvobj.getRecord().get("SOL_END_YRMN");   //해결기간 종료년월
        String   PROD_CD = dvobj.getRecord().get("PROD_CD");   //작품 코드
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   JUDG_BRE = dvobj.getRecord().get("JUDG_BRE");   //판결 내역
        String   PLCST_CD = dvobj.getRecord().get("PLCST_CD");   //경찰서 코드
        double   REQ_ORG_AMT = dvobj.getRecord().getDouble("REQ_ORG_AMT");   //의뢰 원금액
        String   INSPECTOR = dvobj.getRecord().get("INSPECTOR");   //담당 조사관
        String   EVT_NUM = dvobj.getRecord().get("EVT_NUM");   //사건 번호
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //수수료
        String   ACCU_NUM = dobj.getRetObject("SEL16").getRecord().get("ACCU_NUM");   //고소 번호
        String   COMPN_DAY2 = dobj.getRetObject("R").getRecord().get("COMPN_DAY2");   //완결 일자(TEMP)
        String  DISTR_SEQ="";          //분배 순번
        if( ( dobj.getRetObject("R").getRecord().get("JUDG_CD").equals("1") ))
        {
            DISTR_SEQ = dobj.getRetObject("SEL46").getRecord().get("DISTR_SEQ");
        }
        else
        {
            DISTR_SEQ ="";
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String  REPT_DAY="";          //입금일자
        if( ( dobj.getRetObject("R").getRecord().get("JUDG_CD").equals("1") ))
        {
            REPT_DAY = dobj.getRetObject("SEL46").getRecord().get("REPT_DAY");
        }
        else
        {
            REPT_DAY ="";
        }
        String  REPT_GBN="";          //입금 구분
        if( ( dobj.getRetObject("R").getRecord().get("JUDG_CD").equals("1") ))
        {
            REPT_GBN = dobj.getRetObject("SEL46").getRecord().get("REPT_GBN");
        }
        else
        {
            REPT_GBN ="";
        }
        String  REPT_NUM="";          //입금 번호
        if( ( dobj.getRetObject("R").getRecord().get("JUDG_CD").equals("1") ))
        {
            REPT_NUM = dobj.getRetObject("SEL46").getRecord().get("REPT_NUM");
        }
        else
        {
            REPT_NUM ="";
        }
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_ACCU (COMIS, REPT_NUM, INSPRES_ID, EVT_NUM, REPT_GBN, INSPECTOR, REQ_ORG_AMT, PLCST_CD, INS_DATE, JUDG_BRE, STAFF_CD, PROD_CD, SOL_END_YRMN, ACCU_NUM, UPSO_CD, END_YRMN, REPT_DAY, ACCU_DAY, REQ_ADDT_AMT, COMPN_DAY2, ACCU_BRAN, ACCU_GBN, SOL_ADDT_AMT, START_YRMN, DISTR_SEQ, SOL_ORG_AMT, ACCU_OBJ, SOL_START_YRMN, JUDG_CD, EVT_NUM2, REQ_DAY)  \n";
        query +=" values(:COMIS , :REPT_NUM , :INSPRES_ID , :EVT_NUM , :REPT_GBN , :INSPECTOR , :REQ_ORG_AMT , :PLCST_CD , SYSDATE, :JUDG_BRE , :STAFF_CD , :PROD_CD , SUBSTR(:SOL_END_YRMN_1,1,6), :ACCU_NUM , :UPSO_CD , SUBSTR(:END_YRMN_1,1,6), :REPT_DAY , :ACCU_DAY , :REQ_ADDT_AMT , :COMPN_DAY2 , :ACCU_BRAN , :ACCU_GBN , :SOL_ADDT_AMT , SUBSTR(:START_YRMN_1,1,6), :DISTR_SEQ , :SOL_ORG_AMT , :ACCU_OBJ , SUBSTR(:SOL_START_YRMN_1,1,6), :JUDG_CD , :EVT_NUM2 , TO_CHAR(SYSDATE,'YYYYMMDD'))";
        sobj.setSql(query);
        sobj.setString("EVT_NUM2", EVT_NUM2);
        sobj.setString("JUDG_CD", JUDG_CD);               //판결 코드
        sobj.setString("SOL_START_YRMN_1", SOL_START_YRMN_1);               //해결기간 시작년월
        sobj.setString("ACCU_OBJ", ACCU_OBJ);               //고소 대상
        sobj.setDouble("SOL_ORG_AMT", SOL_ORG_AMT);               //해결 원금
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setDouble("SOL_ADDT_AMT", SOL_ADDT_AMT);               //해결 가산금
        sobj.setString("ACCU_GBN", ACCU_GBN);               //고소 구분
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setDouble("REQ_ADDT_AMT", REQ_ADDT_AMT);               //의뢰 가산금액
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("END_YRMN_1", END_YRMN_1);               //종료년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("SOL_END_YRMN_1", SOL_END_YRMN_1);               //해결기간 종료년월
        sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("JUDG_BRE", JUDG_BRE);               //판결 내역
        sobj.setString("PLCST_CD", PLCST_CD);               //경찰서 코드
        sobj.setDouble("REQ_ORG_AMT", REQ_ORG_AMT);               //의뢰 원금액
        sobj.setString("INSPECTOR", INSPECTOR);               //담당 조사관
        sobj.setString("EVT_NUM", EVT_NUM);               //사건 번호
        sobj.setDouble("COMIS", COMIS);               //수수료
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("COMPN_DAY2", COMPN_DAY2);               //완결 일자(TEMP)
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //분배 순번
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        return sobj;
    }
    // 고소정보수정
    public DOBJ CALLaccu_mng_UPD37(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD37");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("UPD37");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLaccu_mng_UPD37(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD37") ;
        rvobj.Println("UPD37");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_UPD37(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //종료년월
        String MOD_DATE ="";        //수정 일시
        String SOL_END_YRMN ="";        //해결기간 종료년월
        String SOL_START_YRMN ="";        //해결기간 시작년월
        String START_YRMN ="";        //시작년월
        String   EVT_NUM2 = dvobj.getRecord().get("EVT_NUM2");
        String   JUDG_CD = dvobj.getRecord().get("JUDG_CD");   //판결 코드
        String   SOL_START_YRMN_1 = dvobj.getRecord().get("SOL_START_YRMN");   //해결기간 시작년월
        String   ACCU_OBJ = dvobj.getRecord().get("ACCU_OBJ");   //고소 대상
        double   SOL_ORG_AMT = dvobj.getRecord().getDouble("SOL_ORG_AMT");   //해결 원금
        String   START_YRMN_1 = dvobj.getRecord().get("START_YRMN");   //시작년월
        double   SOL_ADDT_AMT = dvobj.getRecord().getDouble("SOL_ADDT_AMT");   //해결 가산금
        String   ACCU_GBN = dvobj.getRecord().get("ACCU_GBN");   //고소 구분
        String   ACCU_BRAN = dvobj.getRecord().get("ACCU_BRAN");   //고소 지부
        double   REQ_ADDT_AMT = dvobj.getRecord().getDouble("REQ_ADDT_AMT");   //의뢰 가산금액
        String   ACCU_DAY = dvobj.getRecord().get("ACCU_DAY");   //고소 일자
        String   END_YRMN_1 = dvobj.getRecord().get("END_YRMN");   //종료년월
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   ACCU_NUM = dvobj.getRecord().get("ACCU_NUM");   //고소 번호
        String   SOL_END_YRMN_1 = dvobj.getRecord().get("SOL_END_YRMN");   //해결기간 종료년월
        String   PROD_CD = dvobj.getRecord().get("PROD_CD");   //작품 코드
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   JUDG_BRE = dvobj.getRecord().get("JUDG_BRE");   //판결 내역
        String   PLCST_CD = dvobj.getRecord().get("PLCST_CD");   //경찰서 코드
        double   REQ_ORG_AMT = dvobj.getRecord().getDouble("REQ_ORG_AMT");   //의뢰 원금액
        String   INSPECTOR = dvobj.getRecord().get("INSPECTOR");   //담당 조사관
        String   EVT_NUM = dvobj.getRecord().get("EVT_NUM");   //사건 번호
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //수수료
        String   COMPN_DAY2 = dobj.getRetObject("R").getRecord().get("COMPN_DAY2");   //완결 일자(TEMP)
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_ACCU  \n";
        query +=" set COMIS=:COMIS , MODPRES_ID=:MODPRES_ID , EVT_NUM=:EVT_NUM ,  \n";
        query +=" INSPECTOR=:INSPECTOR ,  \n";
        query +=" REQ_ORG_AMT=:REQ_ORG_AMT , PLCST_CD=:PLCST_CD , JUDG_BRE=:JUDG_BRE , STAFF_CD=:STAFF_CD , PROD_CD=:PROD_CD , SOL_END_YRMN=SUBSTR(:SOL_END_YRMN_1,1,6) , END_YRMN=SUBSTR(:END_YRMN_1,1,6) , REQ_ADDT_AMT=:REQ_ADDT_AMT , ACCU_BRAN=:ACCU_BRAN , COMPN_DAY2=:COMPN_DAY2 , ACCU_GBN=:ACCU_GBN , SOL_ADDT_AMT=:SOL_ADDT_AMT , START_YRMN=SUBSTR(:START_YRMN_1,1,6) ,  \n";
        query +=" SOL_ORG_AMT=:SOL_ORG_AMT , ACCU_OBJ=:ACCU_OBJ , SOL_START_YRMN=SUBSTR(:SOL_START_YRMN_1,1,6) , JUDG_CD=:JUDG_CD , EVT_NUM2=:EVT_NUM2 , MOD_DATE=SYSDATE  \n";
        query +=" where ACCU_NUM=:ACCU_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and ACCU_DAY=:ACCU_DAY";
        sobj.setSql(query);
        sobj.setString("EVT_NUM2", EVT_NUM2);
        sobj.setString("JUDG_CD", JUDG_CD);               //판결 코드
        sobj.setString("SOL_START_YRMN_1", SOL_START_YRMN_1);               //해결기간 시작년월
        sobj.setString("ACCU_OBJ", ACCU_OBJ);               //고소 대상
        sobj.setDouble("SOL_ORG_AMT", SOL_ORG_AMT);               //해결 원금
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setDouble("SOL_ADDT_AMT", SOL_ADDT_AMT);               //해결 가산금
        sobj.setString("ACCU_GBN", ACCU_GBN);               //고소 구분
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setDouble("REQ_ADDT_AMT", REQ_ADDT_AMT);               //의뢰 가산금액
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("END_YRMN_1", END_YRMN_1);               //종료년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("SOL_END_YRMN_1", SOL_END_YRMN_1);               //해결기간 종료년월
        sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("JUDG_BRE", JUDG_BRE);               //판결 내역
        sobj.setString("PLCST_CD", PLCST_CD);               //경찰서 코드
        sobj.setDouble("REQ_ORG_AMT", REQ_ORG_AMT);               //의뢰 원금액
        sobj.setString("INSPECTOR", INSPECTOR);               //담당 조사관
        sobj.setString("EVT_NUM", EVT_NUM);               //사건 번호
        sobj.setDouble("COMIS", COMIS);               //수수료
        sobj.setString("COMPN_DAY2", COMPN_DAY2);               //완결 일자(TEMP)
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 고소메모관리
    public DOBJ CALLaccu_mng_MIUD33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD33");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MIUD33");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLaccu_mng_SEL44(Conn, dobj);           //  SNUM구하기
                dobj  = CALLaccu_mng_INS38(Conn, dobj);           //  등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLaccu_mng_UPD40(Conn, dobj);           //  수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLaccu_mng_DEL41(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    // 기존 메모를 삭제한다.
    public DOBJ CALLaccu_mng_DEL41(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL41");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLaccu_mng_DEL41(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL41") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_DEL41(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SNUM = dvobj.getRecord().get("SNUM");   //일련번호
        String   ACCU_BRAN = dvobj.getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_NUM = dvobj.getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_DAY = dvobj.getRecord().get("ACCU_DAY");   //고소 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_ACCU_MEMO  \n";
        query +=" where ACCU_DAY=:ACCU_DAY  \n";
        query +=" and ACCU_NUM=:ACCU_NUM  \n";
        query +=" and ACCU_BRAN=:ACCU_BRAN  \n";
        query +=" and SNUM=:SNUM";
        sobj.setSql(query);
        sobj.setString("SNUM", SNUM);               //일련번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        return sobj;
    }
    // 수정
    // 기존 메모내용을 수정한다.
    public DOBJ CALLaccu_mng_UPD40(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLaccu_mng_UPD40(dobj, dvobj);
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
    private SQLObject SQLaccu_mng_UPD40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   SNUM = dvobj.getRecord().get("SNUM");   //일련번호
        String   ACCU_BRAN = dvobj.getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_NUM = dvobj.getRecord().get("ACCU_NUM");   //고소 번호
        String   MEMO_NUM = dvobj.getRecord().get("MEMO_NUM");   //메모 번호
        String   ACCU_DAY = dvobj.getRecord().get("ACCU_DAY");   //고소 일자
        String   MEMO = dvobj.getRecord().get("MEMO");   //메모
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_ACCU_MEMO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MEMO=:MEMO , MEMO_NUM=:MEMO_NUM , MOD_DATE=SYSDATE  \n";
        query +=" where ACCU_DAY=:ACCU_DAY  \n";
        query +=" and ACCU_NUM=:ACCU_NUM  \n";
        query +=" and ACCU_BRAN=:ACCU_BRAN  \n";
        query +=" and SNUM=:SNUM";
        sobj.setSql(query);
        sobj.setString("SNUM", SNUM);               //일련번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("MEMO_NUM", MEMO_NUM);               //메모 번호
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("MEMO", MEMO);               //메모
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // SNUM구하기
    // 고소메모 일렬번호 생성
    public DOBJ CALLaccu_mng_SEL44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL44");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL44");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_mng_SEL44(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL44");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_SEL44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("R").getRecord().get("ACCU_DAY");   //고소 일자
        String  ACCU_NUM="";          //고소 번호
        if(!dobj.getRetObject("R").getRecord().get("ACCU_NUM").equals(""))
        {
            ACCU_NUM = dobj.getRetObject("R").getRecord().get("ACCU_NUM");
        }
        else
        {
            ACCU_NUM = dobj.getGVValue("ACCU_NUM");
        }
        String   ACCU_BRAN = dobj.getRetObject("R").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SNUM),  0)  +  1  SNUM  FROM  GIBU.TBRA_ACCU_MEMO  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 등록
    // 신규 데이타 등록
    public DOBJ CALLaccu_mng_INS38(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS38");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLaccu_mng_INS38(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS38") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_INS38(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   ACCU_BRAN = dvobj.getRecord().get("ACCU_BRAN");   //고소 지부
        String   MEMO_DAY = dvobj.getRecord().get("MEMO_DAY");   //메모 일자
        String   MEMO_NUM = dvobj.getRecord().get("MEMO_NUM");   //메모 번호
        String   ACCU_DAY = dvobj.getRecord().get("ACCU_DAY");   //고소 일자
        String   MEMO = dvobj.getRecord().get("MEMO");   //메모
        String  ACCU_NUM="";          //고소 번호
        if(!dobj.getRetObject("R").getRecord().get("ACCU_NUM").equals(""))
        {
            ACCU_NUM = dobj.getRetObject("R").getRecord().get("ACCU_NUM");
        }
        else
        {
            ACCU_NUM = dobj.getGVValue("ACCU_NUM");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String  SNUM="";          //일련번호
        if(!dobj.getRetObject("R").getRecord().get("SNUM").equals(""))
        {
            SNUM = dobj.getRetObject("R").getRecord().get("SNUM");
        }
        else
        {
            SNUM = dobj.getRetObject("SEL44").getRecord().get("SNUM");
        }
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_ACCU_MEMO (INS_DATE, MEMO, ACCU_DAY, INSPRES_ID, MEMO_NUM, MEMO_DAY, ACCU_NUM, ACCU_BRAN, SNUM)  \n";
        query +=" values(SYSDATE, :MEMO , :ACCU_DAY , :INSPRES_ID , :MEMO_NUM , :MEMO_DAY , :ACCU_NUM , :ACCU_BRAN , :SNUM )";
        sobj.setSql(query);
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("MEMO_DAY", MEMO_DAY);               //메모 일자
        sobj.setString("MEMO_NUM", MEMO_NUM);               //메모 번호
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("MEMO", MEMO);               //메모
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("SNUM", SNUM);               //일련번호
        return sobj;
    }
    // 고소정보
    // 등록된 고소 상세 정보를 조회한다
    public DOBJ CALLaccu_mng_SEL42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL42");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL42");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_mng_SEL42(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL42");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_SEL42(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String  ACCU_NUM="";          //고소 번호
        if( ( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I") ))
        {
            ACCU_NUM = dobj.getGVString("ACCU_NUM");
        }
        else
        {
            ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");
        }
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //지부 코드
        String   ACCU_GBN = dobj.getRetObject("S").getRecord().get("ACCU_GBN");   //고소 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.ACCU_DAY  ,  A.ACCU_NUM  ,  A.ACCU_BRAN  ,  A.UPSO_CD  ,  A.ACCU_GBN  ,  ACCU_OBJ  ,  A.PLCST_CD  ,  DECODE(A.ACCU_GBN,  'A',  D.BIGO,  E.CODE_NM)  PLCST_NM  ,  A.EVT_NUM  ,  A.EVT_NUM2  ,  A.INSPECTOR  ,  A.REQ_DAY  ,  A.JUDG_CD  ,  C.CODE_NM  JUDG_NM  ,  A.JUDG_BRE  ,  DECODE(A.START_YRMN,'','',A.START_YRMN  ||'01')  START_YRMN  ,  DECODE(A.END_YRMN,'','',A.END_YRMN  ||'01')  END_YRMN  ,  A.REQ_ORG_AMT  +  A.REQ_ADDT_AMT  REQ_TOT_AMT  ,  A.REQ_ORG_AMT  ,  A.REQ_ADDT_AMT  ,  A.COMPN_DAY2  ,  A.REPT_GBN  ,  DECODE(A.SOL_START_YRMN,'','',A.SOL_START_YRMN  ||'01')  SOL_START_YRMN  ,  DECODE(A.SOL_END_YRMN,'','',A.SOL_END_YRMN  ||'01')  SOL_END_YRMN  ,  A.SOL_ORG_AMT  +  A.SOL_ADDT_AMT  SOL_TOT_AMT  ,  A.SOL_ORG_AMT  ,  A.SOL_ADDT_AMT  ,  A.COMIS  ,  B.STAFF_CD  ,  A.PROD_CD  ,   \n";
        query +=" (SELECT  PROD_TTL  FROM  FIDU.TOPU_PROD  WHERE  PROD_CD  =  A.PROD_CD)  AS  PROD_NM  FROM  GIBU.TBRA_ACCU  A  ,  GIBU.TBRA_UPSO  B  ,  FIDU.TENV_CODE  C  ,  FIDU.TENV_CODE  D  ,  FIDU.TENV_CODE  E  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  A.ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  A.ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  A.ACCU_GBN  =  :ACCU_GBN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00220'   \n";
        query +=" AND  A.JUDG_CD  =  C.CODE_CD(+)   \n";
        query +=" AND  D.HIGH_CD(+)  =  '00214'   \n";
        query +=" AND  D.CODE_CD(+)  =  A.PLCST_CD   \n";
        query +=" AND  E.HIGH_CD(+)  =  '00219'   \n";
        query +=" AND  E.CODE_CD(+)  =  A.PLCST_CD ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ACCU_GBN", ACCU_GBN);               //고소 구분
        return sobj;
    }
    // 고소메모정보
    // 해당업소의 고소메모 리스트를 조회한다.
    public DOBJ CALLaccu_mng_SEL45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL45");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL45");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_mng_SEL45(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL45");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_mng_SEL45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S1").getRecord().get("ACCU_DAY");   //고소 일자
        String  ACCU_NUM="";          //고소 번호
        if(!dobj.getRetObject("S1").getRecord().get("ACCU_NUM").equals(""))
        {
            ACCU_NUM = dobj.getRetObject("S1").getRecord().get("ACCU_NUM");
        }
        else
        {
            ACCU_NUM = dobj.getGVString("ACCU_NUM");
        }
        String   ACCU_BRAN = dobj.getRetObject("S1").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  MEMO_DAY  ,  MEMO_NUM  ,  MEMO  ,  SNUM  FROM  GIBU.TBRA_ACCU_MEMO  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    //##**$$accu_mng
    //##**$$accu_info_print
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/09/23
    * 설명 :
    고소장에 출력할 정보를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLaccu_info_print(DOBJ dobj)
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
            dobj  = CALLaccu_info_print_SEL1(Conn, dobj);           //  고소장출력데이타
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
    public DOBJ CTLaccu_info_print( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_info_print_SEL1(Conn, dobj);           //  고소장출력데이타
        return dobj;
    }
    // 고소장출력데이타
    public DOBJ CALLaccu_info_print_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_info_print_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_info_print_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_GBN = dobj.getRetObject("S").getRecord().get("ACCU_GBN");   //고소 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.ACCU_OBJ,  'B',  B.MNGEMSTR_NM,  B.PERMMSTR_NM)  NAME  ,  DECODE(A.ACCU_OBJ,  'B',  B.MNGEMSTR_NEW_ADDR1  ||  DECODE(B.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||B.MNGEMSTR_NEW_ADDR2)  ||  B.MNGEMSTR_REF_INFO  ,  B.PERMMSTR_NEW_ADDR1  ||  DECODE(B.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||B.PERMMSTR_NEW_ADDR2)  ||  B.PERMMSTR_REF_INFO  )  ADDR  ,  DECODE(A.ACCU_OBJ,  'B',  DECODE(B.MNGEMSTR_PHONNUM,  NULL,  B.MNGEMSTR_HPNUM,  B.MNGEMSTR_PHONNUM),  DECODE(B.PERMMSTR_PHONNUM,  NULL,  B.PERMMSTR_HPNUM,  B.PERMMSTR_PHONNUM))  PHON  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  UPSO_ADDR  ,  B.UPSO_NM  ,  TO_CHAR(SYSDATE,  'YYYYMMDD')  PRINT_DATE  ,  DECODE(A.ACCU_GBN,  'A',  C.BIGO,  D.CODE_NM)  PLCST_NM  FROM  GIBU.TBRA_ACCU  A  ,  GIBU.TBRA_UPSO  B  ,  FIDU.TENV_CODE  C  ,  FIDU.TENV_CODE  D  WHERE  A.ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  A.ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  A.ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  A.ACCU_GBN  =  :ACCU_GBN   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00214'   \n";
        query +=" AND  C.CODE_CD(+)  =  A.PLCST_CD   \n";
        query +=" AND  D.HIGH_CD(+)  =  '00219'   \n";
        query +=" AND  D.CODE_CD(+)  =  A.PLCST_CD ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_GBN", ACCU_GBN);               //고소 구분
        return sobj;
    }
    //##**$$accu_info_print
    //##**$$staff_info_print
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/09/25
    * 설명 :
    위임장에 출력할 정보를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLstaff_info_print(DOBJ dobj)
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
            dobj  = CALLstaff_info_print_SEL1(Conn, dobj);           //  위임장출력정보
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
    public DOBJ CTLstaff_info_print( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstaff_info_print_SEL1(Conn, dobj);           //  위임장출력정보
        return dobj;
    }
    // 위임장출력정보
    public DOBJ CALLstaff_info_print_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_info_print_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_info_print_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_GBN = dobj.getRetObject("S").getRecord().get("ACCU_GBN");   //고소 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.HAN_NM  STAFF_NM  ,  B.RESIINS_NUM  ,  D.ADDR||'  '||D.HNM  ADDR  ,  D.PHON_NUM  ,  A.ACCU_DAY  ,  DECODE(A.ACCU_GBN,  'A',  C.BIGO,  F.CODE_NM)  PLCST_NM  ,  DECODE(A.ACCU_OBJ,  'B',  E.MNGEMSTR_NM,  E.PERMMSTR_NM)  NAME  ,  TO_CHAR(SYSDATE,  'YYYYMMDD')  PRINT_DATE  FROM  GIBU.TBRA_ACCU  A  ,  INSA.TINS_MST01  B  ,  FIDU.TENV_CODE  C  ,  INSA.TCPM_BIPLC  D  ,  GIBU.TBRA_UPSO  E  ,  FIDU.TENV_CODE  F  WHERE  A.ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  A.ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  A.ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  A.ACCU_GBN  =  :ACCU_GBN   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.STAFF_NUM(+)  =  A.STAFF_CD   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00214'   \n";
        query +=" AND  C.CODE_CD(+)  =  A.PLCST_CD   \n";
        query +=" AND  F.HIGH_CD(+)  =  '00219'   \n";
        query +=" AND  F.CODE_CD(+)  =  A.PLCST_CD   \n";
        query +=" AND  E.BRAN_CD(+)  =  D.GIBU   \n";
        query +=" AND  E.UPSO_CD  =  A.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_GBN", ACCU_GBN);               //고소 구분
        return sobj;
    }
    //##**$$staff_info_print
    //##**$$accu_dup_chk
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/12/02
    * 설명 :
    기존에 해결되지 않은 고소가 등록된 업소중에서 다시 새로운 고소등록을 방지한다.
    CNT>0 면 화면상에 "이미 고소가 등록된 업소입니다." 라는 메시지를 보여준다.
    * 수정일1: 2010/03/23
    * 수정자 :
    * 수정내용 :
    해결되지 않은 고소라도 대상기간이 다르면 중복등록이 가능하다.
    EX) 201001~201003으로 고소가 되어있는데 201004~201005이면 중복등록이 되어야 하면 2010/03~이면 중복등록이 되면 안된다.
    ---------박창순T(2010/03/23)
    */
    public DOBJ CTLaccu_dup_chk(DOBJ dobj)
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
            dobj  = CALLaccu_dup_chk_SEL7(Conn, dobj);           //  분기
            if(!dobj.getRetObject("S").getRecord().get("ACCU_DAY").equals(""))
            {
                dobj  = CALLaccu_dup_chk_SEL9(Conn, dobj);           //  중복체크(완료일자NULL)
                dobj  = CALLaccu_dup_chk_MRG12( dobj);        //  SEL9,11 MERGE
                dobj  = CALLaccu_dup_chk_SEL6(Conn, dobj);           //  의뢰일(START_YRMN) 체크
                dobj  = CALLaccu_dup_chk_SEL5(Conn, dobj);           //  의뢰일(END_YRMN) 체크
                dobj  = CALLaccu_dup_chk_SEL1(Conn, dobj);           //  최종결과
            }
            else
            {
                dobj  = CALLaccu_dup_chk_SEL11(Conn, dobj);           //  중복체크(완료일자NULL)
                dobj  = CALLaccu_dup_chk_MRG12( dobj);        //  SEL9,11 MERGE
                dobj  = CALLaccu_dup_chk_SEL6(Conn, dobj);           //  의뢰일(START_YRMN) 체크
                dobj  = CALLaccu_dup_chk_SEL5(Conn, dobj);           //  의뢰일(END_YRMN) 체크
                dobj  = CALLaccu_dup_chk_SEL1(Conn, dobj);           //  최종결과
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
    public DOBJ CTLaccu_dup_chk( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_dup_chk_SEL7(Conn, dobj);           //  분기
        if(!dobj.getRetObject("S").getRecord().get("ACCU_DAY").equals(""))
        {
            dobj  = CALLaccu_dup_chk_SEL9(Conn, dobj);           //  중복체크(완료일자NULL)
            dobj  = CALLaccu_dup_chk_MRG12( dobj);        //  SEL9,11 MERGE
            dobj  = CALLaccu_dup_chk_SEL6(Conn, dobj);           //  의뢰일(START_YRMN) 체크
            dobj  = CALLaccu_dup_chk_SEL5(Conn, dobj);           //  의뢰일(END_YRMN) 체크
            dobj  = CALLaccu_dup_chk_SEL1(Conn, dobj);           //  최종결과
        }
        else
        {
            dobj  = CALLaccu_dup_chk_SEL11(Conn, dobj);           //  중복체크(완료일자NULL)
            dobj  = CALLaccu_dup_chk_MRG12( dobj);        //  SEL9,11 MERGE
            dobj  = CALLaccu_dup_chk_SEL6(Conn, dobj);           //  의뢰일(START_YRMN) 체크
            dobj  = CALLaccu_dup_chk_SEL5(Conn, dobj);           //  의뢰일(END_YRMN) 체크
            dobj  = CALLaccu_dup_chk_SEL1(Conn, dobj);           //  최종결과
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLaccu_dup_chk_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_dup_chk_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_dup_chk_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 중복체크(완료일자NULL)
    // 해결되지 않은 고소정보가 있는 업소를 다시 고소등록하는걸 방지 단 기소중지(JUDG_CD='2')일 경우에는 고소를 새로 등록할수 있다
    public DOBJ CALLaccu_dup_chk_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_dup_chk_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        rvobj.Println("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_dup_chk_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MIN(START_YRMN)  SEL_START_YRMN  ,  MAX(END_YRMN)  SEL_END_YRMN  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =:UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL   \n";
        query +=" AND  ACCU_DAY  <>  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  <>  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  <>  :ACCU_BRAN ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // SEL9,11 MERGE
    // 두값UNION
    public DOBJ CALLaccu_dup_chk_MRG12(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG12");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL9, SEL11","SEL_START_YRMN, SEL_END_YRMN");
        rvobj.setName("MRG12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 의뢰일(START_YRMN) 체크
    public DOBJ CALLaccu_dup_chk_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_dup_chk_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_dup_chk_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REQ_START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //REQ_START_YRMN
        String   END_YRMN = dobj.getRetObject("MRG12").getRecord().get("SEL_END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("MRG12").getRecord().get("SEL_START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CASE  WHEN  :REQ_START_YRMN  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN  THEN  1  ELSE  0  END  S_CNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REQ_START_YRMN", REQ_START_YRMN);               //REQ_START_YRMN
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 의뢰일(END_YRMN) 체크
    public DOBJ CALLaccu_dup_chk_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_dup_chk_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_dup_chk_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REQ_END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //REQ_END_YRMN
        String   END_YRMN = dobj.getRetObject("MRG12").getRecord().get("SEL_END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("MRG12").getRecord().get("SEL_START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CASE  WHEN  :REQ_END_YRMN  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN  THEN  1  ELSE  0  END  E_CNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REQ_END_YRMN", REQ_END_YRMN);               //REQ_END_YRMN
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 최종결과
    public DOBJ CALLaccu_dup_chk_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_dup_chk_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_dup_chk_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   E_CNT = dobj.getRetObject("SEL5").getRecord().get("E_CNT");   //E_CNT
        String   S_CNT = dobj.getRetObject("SEL6").getRecord().get("S_CNT");   //S_CNT
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :S_CNT  +  :E_CNT  AS  CNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("E_CNT", E_CNT);               //E_CNT
        sobj.setString("S_CNT", S_CNT);               //S_CNT
        return sobj;
    }
    // 중복체크(완료일자NULL)
    // 해결되지 않은 고소정보가 있는 업소를 다시 고소등록하는걸 방지 단 기소중지(JUDG_CD='2')일 경우에는 고소를 새로 등록할수 있다
    public DOBJ CALLaccu_dup_chk_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_dup_chk_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.Println("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_dup_chk_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MIN(START_YRMN)  SEL_START_YRMN  ,  MAX(END_YRMN)  SEL_END_YRMN  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =:UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$accu_dup_chk
    //##**$$accu_memo_mng
    /*
    */
    public DOBJ CTLaccu_memo_mng(DOBJ dobj)
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
            dobj  = CALLaccu_memo_mng_MIUD2(Conn, dobj);           //  고소메모관리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLaccu_memo_mng_SEL10(Conn, dobj);           //  고소메모정보
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
    public DOBJ CTLaccu_memo_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_memo_mng_MIUD2(Conn, dobj);           //  고소메모관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLaccu_memo_mng_SEL10(Conn, dobj);           //  고소메모정보
        return dobj;
    }
    // 고소메모관리
    public DOBJ CALLaccu_memo_mng_MIUD2(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLaccu_memo_mng_SEL6(Conn, dobj);           //  SNUM구하기
                dobj  = CALLaccu_memo_mng_INS7(Conn, dobj);           //  등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLaccu_memo_mng_UPD8(Conn, dobj);           //  수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLaccu_memo_mng_DEL9(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    // 기존 메모를 삭제한다.
    public DOBJ CALLaccu_memo_mng_DEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLaccu_memo_mng_DEL9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_memo_mng_DEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SNUM = dvobj.getRecord().get("SNUM");   //일련번호
        String   ACCU_BRAN = dvobj.getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_NUM = dvobj.getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_DAY = dvobj.getRecord().get("ACCU_DAY");   //고소 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_ACCU_MEMO  \n";
        query +=" where ACCU_DAY=:ACCU_DAY  \n";
        query +=" and ACCU_NUM=:ACCU_NUM  \n";
        query +=" and ACCU_BRAN=:ACCU_BRAN  \n";
        query +=" and SNUM=:SNUM";
        sobj.setSql(query);
        sobj.setString("SNUM", SNUM);               //일련번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        return sobj;
    }
    // 수정
    // 기존 메모내용을 수정한다.
    public DOBJ CALLaccu_memo_mng_UPD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLaccu_memo_mng_UPD8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_memo_mng_UPD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   SNUM = dvobj.getRecord().get("SNUM");   //일련번호
        String   ACCU_BRAN = dvobj.getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_NUM = dvobj.getRecord().get("ACCU_NUM");   //고소 번호
        String   MEMO_NUM = dvobj.getRecord().get("MEMO_NUM");   //메모 번호
        String   ACCU_DAY = dvobj.getRecord().get("ACCU_DAY");   //고소 일자
        String   MEMO = dvobj.getRecord().get("MEMO");   //메모
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_ACCU_MEMO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MEMO=:MEMO , MEMO_NUM=:MEMO_NUM , MOD_DATE=SYSDATE  \n";
        query +=" where ACCU_DAY=:ACCU_DAY  \n";
        query +=" and ACCU_NUM=:ACCU_NUM  \n";
        query +=" and ACCU_BRAN=:ACCU_BRAN  \n";
        query +=" and SNUM=:SNUM";
        sobj.setSql(query);
        sobj.setString("SNUM", SNUM);               //일련번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("MEMO_NUM", MEMO_NUM);               //메모 번호
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("MEMO", MEMO);               //메모
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // SNUM구하기
    // 고소메모 일렬번호 생성
    public DOBJ CALLaccu_memo_mng_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_memo_mng_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_memo_mng_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("R").getRecord().get("ACCU_DAY");   //고소 일자
        String  ACCU_NUM="";          //고소 번호
        if(!dobj.getRetObject("R").getRecord().get("ACCU_NUM").equals(""))
        {
            ACCU_NUM = dobj.getRetObject("R").getRecord().get("ACCU_NUM");
        }
        else
        {
            ACCU_NUM = dobj.getGVValue("ACCU_NUM");
        }
        String   ACCU_BRAN = dobj.getRetObject("R").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SNUM),  0)  +  1  SNUM  FROM  GIBU.TBRA_ACCU_MEMO  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 등록
    // 신규 데이타 등록
    public DOBJ CALLaccu_memo_mng_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLaccu_memo_mng_INS7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_memo_mng_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   ACCU_BRAN = dvobj.getRecord().get("ACCU_BRAN");   //고소 지부
        String   MEMO_DAY = dvobj.getRecord().get("MEMO_DAY");   //메모 일자
        String   MEMO_NUM = dvobj.getRecord().get("MEMO_NUM");   //메모 번호
        String   ACCU_DAY = dvobj.getRecord().get("ACCU_DAY");   //고소 일자
        String   MEMO = dvobj.getRecord().get("MEMO");   //메모
        String  ACCU_NUM="";          //고소 번호
        if(!dobj.getRetObject("R").getRecord().get("ACCU_NUM").equals(""))
        {
            ACCU_NUM = dobj.getRetObject("R").getRecord().get("ACCU_NUM");
        }
        else
        {
            ACCU_NUM = dobj.getGVValue("ACCU_NUM");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String  SNUM="";          //일련번호
        if(!dobj.getRetObject("R").getRecord().get("SNUM").equals(""))
        {
            SNUM = dobj.getRetObject("R").getRecord().get("SNUM");
        }
        else
        {
            SNUM = dobj.getRetObject("SEL6").getRecord().get("SNUM");
        }
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_ACCU_MEMO (INS_DATE, MEMO, ACCU_DAY, INSPRES_ID, MEMO_NUM, MEMO_DAY, ACCU_NUM, ACCU_BRAN, SNUM)  \n";
        query +=" values(SYSDATE, :MEMO , :ACCU_DAY , :INSPRES_ID , :MEMO_NUM , :MEMO_DAY , :ACCU_NUM , :ACCU_BRAN , :SNUM )";
        sobj.setSql(query);
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("MEMO_DAY", MEMO_DAY);               //메모 일자
        sobj.setString("MEMO_NUM", MEMO_NUM);               //메모 번호
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("MEMO", MEMO);               //메모
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("SNUM", SNUM);               //일련번호
        return sobj;
    }
    // 고소메모정보
    // 해당업소의 고소메모 리스트를 조회한다.
    public DOBJ CALLaccu_memo_mng_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_memo_mng_SEL10(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_memo_mng_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String  ACCU_NUM="";          //고소 번호
        if(!dobj.getRetObject("S").getRecord().get("ACCU_NUM").equals(""))
        {
            ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");
        }
        else
        {
            ACCU_NUM = dobj.getGVString("ACCU_NUM");
        }
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  MEMO_DAY  ,  MEMO_NUM  ,  MEMO  ,  SNUM  FROM  GIBU.TBRA_ACCU_MEMO  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    //##**$$accu_memo_mng
    //##**$$p_accu_list
    /* * 프로그램명 : bra05_s03
    * 작성자 : 서정재
    * 작성일 : 2009/12/04
    * 설명 :
    고소 상세조회시 정확한 고소번호를 모를경우 조회조건에 해당하는 고소정보를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLp_accu_list(DOBJ dobj)
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
            dobj  = CALLp_accu_list_SEL1(Conn, dobj);           //  고소리스트
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
    public DOBJ CTLp_accu_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLp_accu_list_SEL1(Conn, dobj);           //  고소리스트
        return dobj;
    }
    // 고소리스트
    public DOBJ CALLp_accu_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_accu_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_accu_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   MNGEMSTR_NM = dobj.getRetObject("S").getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ACCU_GBN = dobj.getRetObject("S").getRecord().get("ACCU_GBN");   //고소 구분
        String   PERMMSTR_NM = dobj.getRetObject("S").getRecord().get("PERMMSTR_NM");   //허가주 이름
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT XB.UPSO_CD , XB.UPSO_NM , DECODE(XA.ACCU_GBN,'A','형사','민사') ACCU_GBN_NM , XC.CODE_NM JUDG_NM , XA.ACCU_DAY || XA.ACCU_NUM || XA.ACCU_BRAN ACCU_NO , XB.MNGEMSTR_NM , XB.PERMMSTR_NM , XD.GRADNM , XB.BRAN_CD , XA.ACCU_DAY , XA.ACCU_NUM , XA.ACCU_BRAN , XA.ACCU_GBN , XA.COMPN_DAY  ";
        query +=" FROM GIBU.TBRA_ACCU XA , GIBU.TBRA_UPSO XB , FIDU.TENV_CODE XC , (  ";
        query +=" SELECT ZA.UPSO_CD, ZB.MONPRNCFEE, ZC.GRADNM, TRIM(ZB.BSTYP_CD) BSTYP_CD, ZB.UPSO_GRAD FROM(  ";
        query +=" SELECT A.UPSO_CD, MAX(A.JOIN_SEQ) JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE B.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" GROUP BY A.UPSO_CD ) ZA , GIBU.TBRA_UPSORTAL_INFO ZB , GIBU.TBRA_BSTYPGRAD ZC  ";
        query +=" WHERE ZB.JOIN_SEQ = ZA.JOIN_SEQ  ";
        query +=" AND ZB.UPSO_CD = ZA.UPSO_CD  ";
        query +=" AND ZC.BSTYP_CD = ZB.BSTYP_CD  ";
        query +=" AND ZC.GRAD_GBN = ZB.UPSO_GRAD ) XD  ";
        query +=" WHERE XB.BRAN_CD = :BRAN_CD  ";
        query +=" AND XA.UPSO_CD = XB.UPSO_CD  ";
        query +=" AND XD.UPSO_CD = XB.UPSO_CD  ";
        query +=" AND XC.HIGH_CD(+) = '00220'  ";
        query +=" AND XC.CODE_CD(+) = XA.JUDG_CD  ";
        if( !ACCU_GBN.equals(""))
        {
            query +=" AND XA.ACCU_GBN LIKE :ACCU_GBN||'%'  ";
        }
        if( !ACCU_DAY.equals(""))
        {
            query +=" AND XA.ACCU_DAY LIKE :ACCU_DAY||'%'  ";
        }
        if( !ACCU_NUM.equals(""))
        {
            query +=" AND XA.ACCU_NUM = :ACCU_NUM  ";
        }
        if( !ACCU_BRAN.equals(""))
        {
            query +=" AND XA.ACCU_BRAN = :ACCU_BRAN  ";
        }
        if( !UPSO_NM.equals(""))
        {
            query +=" AND XB.UPSO_NM LIKE '%'|| :UPSO_NM || '%'  ";
        }
        if( !MNGEMSTR_NM.equals(""))
        {
            query +=" AND XB.MNGEMSTR_NM LIKE :MNGEMSTR_NM || '%'  ";
        }
        if( !PERMMSTR_NM.equals(""))
        {
            query +=" AND XB.PERMMSTR_NM LIKE :PERMMSTR_NM || '%'  ";
        }
        query +=" ORDER BY XA.ACCU_DAY  ";
        sobj.setSql(query);
        if( !ACCU_DAY.equals(""))
        {
            sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        }
        if( !ACCU_NUM.equals(""))
        {
            sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        }
        if( !ACCU_BRAN.equals(""))
        {
            sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        }
        if( !UPSO_NM.equals(""))
        {
            sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        }
        if( !MNGEMSTR_NM.equals(""))
        {
            sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        if( !ACCU_GBN.equals(""))
        {
            sobj.setString("ACCU_GBN", ACCU_GBN);               //고소 구분
        }
        if( !PERMMSTR_NM.equals(""))
        {
            sobj.setString("PERMMSTR_NM", PERMMSTR_NM);               //허가주 이름
        }
        return sobj;
    }
    //##**$$p_accu_list
    //##**$$accu_comp_chk
    /*
    */
    public DOBJ CTLaccu_comp_chk(DOBJ dobj)
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
            dobj  = CALLaccu_comp_chk_SEL1(Conn, dobj);
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
    public DOBJ CTLaccu_comp_chk( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_comp_chk_SEL1(Conn, dobj);
        return dobj;
    }
    public DOBJ CALLaccu_comp_chk_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_comp_chk_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_comp_chk_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$accu_comp_chk
    //##**$$sel_accu_attch
    /*
    */
    public DOBJ CTLsel_accu_attch(DOBJ dobj)
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
            dobj  = CALLsel_accu_attch_SEL1(Conn, dobj);           //  진술서조회
            dobj  = CALLsel_accu_attch_SEL2(Conn, dobj);           //  최고서조회
            dobj  = CALLsel_accu_attch_SEL3(Conn, dobj);           //  사건통지서조회
            dobj  = CALLsel_accu_attch_SEL4(Conn, dobj);           //  고소장조회
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
    public DOBJ CTLsel_accu_attch( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_accu_attch_SEL1(Conn, dobj);           //  진술서조회
        dobj  = CALLsel_accu_attch_SEL2(Conn, dobj);           //  최고서조회
        dobj  = CALLsel_accu_attch_SEL3(Conn, dobj);           //  사건통지서조회
        dobj  = CALLsel_accu_attch_SEL4(Conn, dobj);           //  고소장조회
        return dobj;
    }
    // 진술서조회
    public DOBJ CALLsel_accu_attch_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_accu_attch_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_accu_attch_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  UPSO_CD  ,  SEQ  ,  FILE_NM  ,  FILE_ROUT  AS  SVR_FILE_ROUT  ,  FILE_TYPE  ,  FILE_TEMPNM  AS  SVR_FILE_NM  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  'ADVT' ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 최고서조회
    public DOBJ CALLsel_accu_attch_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_accu_attch_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_accu_attch_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  UPSO_CD  ,  SEQ  ,  FILE_NM  ,  FILE_ROUT  AS  SVR_FILE_ROUT  ,  FILE_TYPE  ,  FILE_TEMPNM  AS  SVR_FILE_NM  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  'BPAP' ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 사건통지서조회
    public DOBJ CALLsel_accu_attch_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_accu_attch_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_accu_attch_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  UPSO_CD  ,  SEQ  ,  FILE_NM  ,  FILE_ROUT  AS  SVR_FILE_ROUT  ,  FILE_TYPE  ,  FILE_TEMPNM  AS  SVR_FILE_NM  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  'EVTD'  ORDER  BY  SEQ ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 고소장조회
    public DOBJ CALLsel_accu_attch_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_accu_attch_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_accu_attch_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  UPSO_CD  ,  SEQ  ,  FILE_NM  ,  FILE_ROUT  AS  SVR_FILE_ROUT  ,  FILE_TYPE  ,  FILE_TEMPNM  AS  SVR_FILE_NM  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  'COMP'  ORDER  BY  SEQ ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    //##**$$sel_accu_attch
    //##**$$accu_delete
    /*
    */
    public DOBJ CTLaccu_delete(DOBJ dobj)
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
            dobj  = CALLaccu_delete_XIUD3(Conn, dobj);           //  고소내역삭제
            dobj  = CALLaccu_delete_XIUD4(Conn, dobj);           //  고소메모삭제
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
    public DOBJ CTLaccu_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_delete_XIUD3(Conn, dobj);           //  고소내역삭제
        dobj  = CALLaccu_delete_XIUD4(Conn, dobj);           //  고소메모삭제
        return dobj;
    }
    // 고소내역삭제
    public DOBJ CALLaccu_delete_XIUD3(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLaccu_delete_XIUD3(dobj, dvobj);
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
    private SQLObject SQLaccu_delete_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_ACCU  \n";
        query +=" WHERE ACCU_DAY = :ACCU_DAY  \n";
        query +=" AND ACCU_NUM = :ACCU_NUM  \n";
        query +=" AND ACCU_BRAN = :ACCU_BRAN";
        sobj.setSql(query);
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        return sobj;
    }
    // 고소메모삭제
    public DOBJ CALLaccu_delete_XIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD4");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLaccu_delete_XIUD4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_delete_XIUD4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_ACCU_MEMO  \n";
        query +=" WHERE ACCU_DAY = :ACCU_DAY  \n";
        query +=" AND ACCU_NUM = :ACCU_NUM  \n";
        query +=" AND ACCU_BRAN = :ACCU_BRAN";
        sobj.setSql(query);
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        return sobj;
    }
    //##**$$accu_delete
    //##**$$end
}
