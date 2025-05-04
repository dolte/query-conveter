
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s02_1
{
    public bra03_s02_1()
    {
    }
    //##**$$indtnpaper_insert
    /* * 프로그램명 : bra03_s02
    * 작성자 : 박태현
    * 작성일 : 2009/10/22
    * 설명    : UI의 개별지로 청구등록/수정/삭제 요청에 대해 각각의 청구내역을 저장/수정/삭제 처리 후 결과와 사용료 정보를 리턴한다.
    INPUT
    BRAN_CD (지부 코드)
    BSTYP_CD (업종 코드 )
    CRET_GBN (OCR/MICR 생성 구분)
    DEMD_DAY (청구 일자)
    DEMD_GBN (청구 구분)
    DEMD_MMCNT (청구개월수)
    DEMD_NUM (청구서번호)
    DSCT_AMT (할인 금액 - 자동이체시 할인)
    END_YRMN (종료년월)
    INSPRES_ID (등록자 ID)
    IUDFLAG (레코드상태구분)
    MONPRNCFEE (월정료)
    REMAK (비고)
    START_YRMN (시작년월)
    TOT_ADDT_AMT (총 가산 금액)
    TOT_DEMD_AMT (총 청구 금액)
    TOT_EADDT_AMT (총 중가산 금액)
    UPSO_CD (업소 코드)
    UPSO_GRAD (업소 등급)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLindtnpaper_insert(DOBJ dobj)
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
            dobj  = CALLindtnpaper_insert_SEL20(Conn, dobj);           //  DEMD_NUM생성
            dobj  = CALLindtnpaper_insert_MIUD3(Conn, dobj);           //  등록및수정
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLindtnpaper_insert_SEL19(Conn, dobj);           //  청구결과리턴
            dobj  = CALLindtnpaper_insert_SEL18(Conn, dobj);           //  사용료변경정보
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
    public DOBJ CTLindtnpaper_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtnpaper_insert_SEL20(Conn, dobj);           //  DEMD_NUM생성
        dobj  = CALLindtnpaper_insert_MIUD3(Conn, dobj);           //  등록및수정
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLindtnpaper_insert_SEL19(Conn, dobj);           //  청구결과리턴
        dobj  = CALLindtnpaper_insert_SEL18(Conn, dobj);           //  사용료변경정보
        return dobj;
    }
    // DEMD_NUM생성
    public DOBJ CALLindtnpaper_insert_SEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL20");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL20");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtnpaper_insert_SEL20(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL20");
        rvobj.Println("SEL20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_insert_SEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(DEMD_NUM),  0)  +  1,  4,  '0')  DEMD_NUM  FROM  GIBU.TBRA_DEMD_INDTN  WHERE  DEMD_DAY  =  :DEMD_DAY   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 등록및수정
    public DOBJ CALLindtnpaper_insert_MIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLindtnpaper_insert_SEL13(Conn, dobj);           //  청구월 조회
                dobj  = CALLindtnpaper_insert_OSP14(Conn, dobj);           //  개별월 청구값 저장
                dobj  = CALLindtnpaper_insert_INS2(Conn, dobj);           //  등록
                dobj  = CALLindtnpaper_insert_XIUD12(Conn, dobj);           //  BALANCE 값 등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLindtnpaper_insert_DEL16(Conn, dobj);           //  삭제
                dobj  = CALLindtnpaper_insert_DEL10(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    public DOBJ CALLindtnpaper_insert_DEL16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLindtnpaper_insert_DEL16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_insert_DEL16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   DEMD_DAY = dvobj.getRecord().get("DEMD_DAY");   //청구 일자
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_DEMD_INDTN  \n";
        query +=" where DEMD_NUM=:DEMD_NUM  \n";
        query +=" and DEMD_DAY=:DEMD_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    // 청구월 조회
    public DOBJ CALLindtnpaper_insert_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtnpaper_insert_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_insert_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  :START_YRMN  AS  START_YRMN  ,  :END_YRMN  AS  END_YRMN  ,MAX(DEMD_YRMN)  AS  DEMD_YRMN  ,  'M'  AS  CRET_GBN  ,  'MICR_MM_EUNDORI'  AS  INSPRES_ID  FROM  GIBU.TBRA_DEMD_OCR ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 삭제
    public DOBJ CALLindtnpaper_insert_DEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLindtnpaper_insert_DEL10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_insert_DEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   DEMD_DAY = dvobj.getRecord().get("DEMD_DAY");   //청구 일자
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_DEMD_INDTN_MM  \n";
        query +=" where DEMD_NUM=:DEMD_NUM  \n";
        query +=" and DEMD_DAY=:DEMD_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    // 개별월 청구값 저장
    public DOBJ CALLindtnpaper_insert_OSP14(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP14");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL13");         //사용자 화면에서 발생한 Object입니다.
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","DEMD_YRMN","CRET_GBN","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_DEMD_MM_AMT";
        int[]    intypes={12, 12, 12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP14");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 등록
    public DOBJ CALLindtnpaper_insert_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLindtnpaper_insert_INS2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        rvobj.Println("INS2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_insert_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //총 청구 금액
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //총 가산 금액
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //시작년월
        double   TOT_EADDT_AMT = dvobj.getRecord().getDouble("TOT_EADDT_AMT");   //총 중가산 금액
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //업종 코드
        String   DEMD_GBN = dvobj.getRecord().get("DEMD_GBN");   //청구 구분
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //월정료
        double   DEMD_MMCNT = dvobj.getRecord().getDouble("DEMD_MMCNT");   //청구개월수
        double   DSCT_AMT = dvobj.getRecord().getDouble("DSCT_AMT");   //할인 금액 - 자동이체시 할인
        String   DEMD_DAY = dvobj.getRecord().get("DEMD_DAY");   //청구 일자
        String   UPSO_GRAD = dvobj.getRecord().get("UPSO_GRAD");   //업소 등급
        String   CRET_GBN = dvobj.getRecord().get("CRET_GBN");   //OCR/MICR 생성 구분
        String   DEMD_NUM = dobj.getRetObject("SEL20").getRecord().get("DEMD_NUM");   //청구 번호
        String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_DEMD_INDTN (INSPRES_ID, CRET_GBN, UPSO_GRAD, DEMD_DAY, DSCT_AMT, DEMD_MMCNT, MONPRNCFEE, DEMD_GBN, BSTYP_CD, TOT_EADDT_AMT, START_YRMN, DEMD_NUM, INS_DATE, TOT_ADDT_AMT, TOT_DEMD_AMT, UPSO_CD, BRAN_CD, END_YRMN, REMAK)  \n";
        query +=" values(:INSPRES_ID , :CRET_GBN , :UPSO_GRAD , :DEMD_DAY , :DSCT_AMT , :DEMD_MMCNT , :MONPRNCFEE , :DEMD_GBN , :BSTYP_CD , :TOT_EADDT_AMT , :START_YRMN , :DEMD_NUM , SYSDATE, :TOT_ADDT_AMT , :TOT_DEMD_AMT , :UPSO_CD , :BRAN_CD , :END_YRMN , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //총 청구 금액
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //총 가산 금액
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        sobj.setDouble("TOT_EADDT_AMT", TOT_EADDT_AMT);               //총 중가산 금액
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("DEMD_GBN", DEMD_GBN);               //청구 구분
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //월정료
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //청구개월수
        sobj.setDouble("DSCT_AMT", DSCT_AMT);               //할인 금액 - 자동이체시 할인
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("UPSO_GRAD", UPSO_GRAD);               //업소 등급
        sobj.setString("CRET_GBN", CRET_GBN);               //OCR/MICR 생성 구분
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // BALANCE 값 등록
    public DOBJ CALLindtnpaper_insert_XIUD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD12");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLindtnpaper_insert_XIUD12(dobj, dvobj);
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
    private SQLObject SQLindtnpaper_insert_XIUD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //청구 일자
        String   DEMD_NUM = dobj.getRetObject("SEL20").getRecord().get("DEMD_NUM");   //청구서번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_INDTN  \n";
        query +=" SET BALANCE = (SELECT SUM(BALANCE) FROM GIBU.TBRA_DEMD_INDTN_MM  \n";
        query +=" WHERE DEMD_DAY = :DEMD_DAY  \n";
        query +=" AND DEMD_NUM = :DEMD_NUM  \n";
        query +=" AND UPSO_CD = :UPSO_CD )  \n";
        query +=" WHERE DEMD_DAY = :DEMD_DAY  \n";
        query +=" AND DEMD_NUM = :DEMD_NUM  \n";
        query +=" AND UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구결과리턴
    public DOBJ CALLindtnpaper_insert_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtnpaper_insert_SEL19(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        rvobj.setRetcode(1);
        rvobj.Println("SEL19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_insert_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String  DEMD_NUM="";          //청구 번호
        if( ( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I") ))
        {
            DEMD_NUM = dobj.getRetObject("SEL20").getRecord().get("DEMD_NUM");
        }
        else
        {
            DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");
        }
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.DEMD_DAY  ,  XB.DEMD_NUM  ,  XB.BRAN_CD  ,  XA.MNGEMSTR_NM  ,  XC.BSTYP_CD  ,  XC.UPSO_GRAD  ,  TRIM(XC.BSTYP_CD)  ||  XC.UPSO_GRAD  GRAD  ,  XC.GRADNM  ,  XC.MONPRNCFEE  ,  XA.STAFF_CD  ,  XE.HAN_NM  STAFF_NM  ,  XA.OPBI_DAY  ,  XA.NEW_DAY  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XB.START_YRMN  ||  '01'  START_YRMN  ,  XB.END_YRMN  ||  '01'  END_YRMN  ,  XB.TOT_DEMD_AMT  -  XB.TOT_ADDT_AMT  -  XB.TOT_EADDT_AMT  TOT_USE_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_DEMD_AMT  ,  XB.REMAK  ,  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,  8)  LAST_YRMN  ,  XA.MCHNDAESU  ACMCN_DAESU  ,  XB.TOT_EADDT_AMT  ,  XD.END_DATE  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_DEMD_INDTN  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  (   \n";
        query +=" SELECT  MAX(END_YEAR  ||  END_MON  ||  END_DAY)  END_DATE  FROM  GIBU.TBRA_BRANEND  WHERE  BRAN_CD  =  :BRAN_CD  )  XD  ,  INSA.TINS_MST01  XE  WHERE  XB.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.DEMD_DAY  =  :DEMD_DAY   \n";
        query +=" AND  XB.DEMD_NUM  =  :DEMD_NUM   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XA.STAFF_CD  =  XE.STAFF_NUM(+) ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구 번호
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 사용료변경정보
    public DOBJ CALLindtnpaper_insert_SEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL18");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL18");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtnpaper_insert_SEL18(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL18");
        rvobj.setRetcode(1);
        rvobj.Println("SEL18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_insert_SEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CHG_DAY  ,  CHG_NUM  ,  CHG_BRAN  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  ,  MONPRNCFEE  ,  APPL_DAY  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =:UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$indtnpaper_insert
    //##**$$end
}
