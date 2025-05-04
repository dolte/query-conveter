
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_t16_2
{
    public bra04_t16_2()
    {
    }
    //##**$$adj_mng
    /*
    */
    public DOBJ CTLadj_mng(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().getDouble("SEQ") > 0)
            {
                dobj  = CALLadj_mng_UPD6(Conn, dobj);           //  정정금액 등록
            }
            else
            {
                dobj  = CALLadj_mng_SEL9(Conn, dobj);           //  SEQ 획득
                dobj  = CALLadj_mng_INS8(Conn, dobj);           //  정정금액 등록
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
    public DOBJ CTLadj_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().getDouble("SEQ") > 0)
        {
            dobj  = CALLadj_mng_UPD6(Conn, dobj);           //  정정금액 등록
        }
        else
        {
            dobj  = CALLadj_mng_SEL9(Conn, dobj);           //  SEQ 획득
            dobj  = CALLadj_mng_INS8(Conn, dobj);           //  정정금액 등록
        }
        return dobj;
    }
    // 정정금액 등록
    // 해당일의 미수정보가 있을때
    public DOBJ CALLadj_mng_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLadj_mng_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLadj_mng_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double   COMIS_AMT = dvobj.getRecord().getDouble("COMIS_AMT");   //수수료 금액
        String   BEFORE_ADJ_AMT = dvobj.getRecord().get("BEFORE_ADJ_AMT");
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   ADJ_GBN = dvobj.getRecord().get("ADJ_GBN");   //조정 구분
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   NONPY_DAY = dvobj.getRecord().get("NONPY_DAY");   //미납일자
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //조정 금액
        String   BIGO = dvobj.getRecord().get("BIGO");   //비고사항
        String   BEFORE_COMIS_AMT = dvobj.getRecord().get("BEFORE_COMIS_AMT");
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_MISU_ADJ  \n";
        query +=" set  \n";
        query +=" BEFORE_COMIS_AMT=:BEFORE_COMIS_AMT , INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID , BIGO=:BIGO , ADJ_AMT=:ADJ_AMT , ADJ_GBN=:ADJ_GBN ,  \n";
        query +=" BEFORE_ADJ_AMT=:BEFORE_ADJ_AMT , COMIS_AMT=:COMIS_AMT  \n";
        query +=" where NONPY_DAY=:NONPY_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("COMIS_AMT", COMIS_AMT);               //수수료 금액
        sobj.setString("BEFORE_ADJ_AMT", BEFORE_ADJ_AMT);
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("ADJ_GBN", ADJ_GBN);               //조정 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("NONPY_DAY", NONPY_DAY);               //미납일자
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //조정 금액
        sobj.setString("BIGO", BIGO);               //비고사항
        sobj.setString("BEFORE_COMIS_AMT", BEFORE_COMIS_AMT);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // SEQ 획득
    public DOBJ CALLadj_mng_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLadj_mng_SEL9(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        rvobj.Println("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLadj_mng_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NONPY_DAY = dobj.getRetObject("S").getRecord().get("NONPY_DAY");   //미납일자
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_MISU_ADJ  WHERE  NONPY_DAY  =  :NONPY_DAY   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("NONPY_DAY", NONPY_DAY);               //미납일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 정정금액 등록
    public DOBJ CALLadj_mng_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS8");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLadj_mng_INS8(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        rvobj.Println("INS8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLadj_mng_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double   COMIS_AMT = dvobj.getRecord().getDouble("COMIS_AMT");   //수수료 금액
        String   BEFORE_ADJ_AMT = dvobj.getRecord().get("BEFORE_ADJ_AMT");
        String   ADJ_GBN = dvobj.getRecord().get("ADJ_GBN");   //조정 구분
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   NONPY_DAY = dvobj.getRecord().get("NONPY_DAY");   //미납일자
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //조정 금액
        String   BIGO = dvobj.getRecord().get("BIGO");   //비고사항
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   BEFORE_COMIS_AMT = dvobj.getRecord().get("BEFORE_COMIS_AMT");
        double   SEQ = dobj.getRetObject("SEL9").getRecord().getDouble("SEQ");   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_MISU_ADJ (BEFORE_COMIS_AMT, INS_DATE, INSPRES_ID, BIGO, ADJ_AMT, NONPY_DAY, UPSO_CD, ADJ_GBN, SEQ, BEFORE_ADJ_AMT, COMIS_AMT)  \n";
        query +=" values(:BEFORE_COMIS_AMT , SYSDATE, :INSPRES_ID , :BIGO , :ADJ_AMT , :NONPY_DAY , :UPSO_CD , :ADJ_GBN , :SEQ , :BEFORE_ADJ_AMT , :COMIS_AMT )";
        sobj.setSql(query);
        sobj.setDouble("COMIS_AMT", COMIS_AMT);               //수수료 금액
        sobj.setString("BEFORE_ADJ_AMT", BEFORE_ADJ_AMT);
        sobj.setString("ADJ_GBN", ADJ_GBN);               //조정 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("NONPY_DAY", NONPY_DAY);               //미납일자
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //조정 금액
        sobj.setString("BIGO", BIGO);               //비고사항
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("BEFORE_COMIS_AMT", BEFORE_COMIS_AMT);
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    //##**$$adj_mng
    //##**$$end
}
