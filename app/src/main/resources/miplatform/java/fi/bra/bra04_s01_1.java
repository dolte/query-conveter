
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s01_1
{
    public bra04_s01_1()
    {
    }
    //##**$$indtn_rept_save2
    /*
    */
    public DOBJ CTLindtn_rept_save2(DOBJ dobj)
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
            dobj  = CALLindtn_rept_save2_DEL1(Conn, dobj);           //  오류 내역 삭제
            if( dobj.getRetObject("S").getRecord().get("CRUD").equals("I"))
            {
                dobj  = CALLindtn_rept_save2_SEL2(Conn, dobj);           //  고소중 업소확인
                if( dobj.getRetObject("S").getRecord().get("CLAIM_GBN").equals("1"))
                {
                    dobj  = CALLindtn_rept_save2_OSP1(Conn, dobj);           //  개별 채권 입금 저장
                    dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
                }
                else if( dobj.getRetObject("SEL2").getRecord().getDouble("ACCU_CNT") == 0)
                {
                    dobj  = CALLindtn_rept_save2_OSP2(Conn, dobj);           //  개별 입금
                    dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
                }
                else
                {
                    dobj  = CALLindtn_rept_save2_OSP3(Conn, dobj);           //  고소 개별 입금
                    dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
                }
            }
            else
            {
                dobj  = CALLindtn_rept_save2_SEL8(Conn, dobj);           //  고소 입금 확인
                if( dobj.getRetObject("SEL8").getRecord().get("ACCU_GBN").equals("06"))
                {
                    dobj  = CALLindtn_rept_save2_OSP4(Conn, dobj);           //  개별 채권 입금 삭제
                    dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
                }
                else if(!dobj.getRetObject("SEL8").getRecord().get("ACCU_GBN").equals("22"))
                {
                    dobj  = CALLindtn_rept_save2_OSP5(Conn, dobj);           //  개별 입금 삭제
                    dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
                }
                else
                {
                    dobj  = CALLindtn_rept_save2_OSP11(Conn, dobj);           //  개별 고소 입금 삭제
                    dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
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
    public DOBJ CTLindtn_rept_save2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtn_rept_save2_DEL1(Conn, dobj);           //  오류 내역 삭제
        if( dobj.getRetObject("S").getRecord().get("CRUD").equals("I"))
        {
            dobj  = CALLindtn_rept_save2_SEL2(Conn, dobj);           //  고소중 업소확인
            if( dobj.getRetObject("S").getRecord().get("CLAIM_GBN").equals("1"))
            {
                dobj  = CALLindtn_rept_save2_OSP1(Conn, dobj);           //  개별 채권 입금 저장
                dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
            }
            else if( dobj.getRetObject("SEL2").getRecord().getDouble("ACCU_CNT") == 0)
            {
                dobj  = CALLindtn_rept_save2_OSP2(Conn, dobj);           //  개별 입금
                dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
            }
            else
            {
                dobj  = CALLindtn_rept_save2_OSP3(Conn, dobj);           //  고소 개별 입금
                dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
            }
        }
        else
        {
            dobj  = CALLindtn_rept_save2_SEL8(Conn, dobj);           //  고소 입금 확인
            if( dobj.getRetObject("SEL8").getRecord().get("ACCU_GBN").equals("06"))
            {
                dobj  = CALLindtn_rept_save2_OSP4(Conn, dobj);           //  개별 채권 입금 삭제
                dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
            }
            else if(!dobj.getRetObject("SEL8").getRecord().get("ACCU_GBN").equals("22"))
            {
                dobj  = CALLindtn_rept_save2_OSP5(Conn, dobj);           //  개별 입금 삭제
                dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
            }
            else
            {
                dobj  = CALLindtn_rept_save2_OSP11(Conn, dobj);           //  개별 고소 입금 삭제
                dobj  = CALLindtn_rept_save2_SEL3(Conn, dobj);           //  오류내역 조회
            }
        }
        return dobj;
    }
    // 오류 내역 삭제
    public DOBJ CALLindtn_rept_save2_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLindtn_rept_save2_DEL1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtn_rept_save2_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_ERR  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 고소중 업소확인
    // 고소중인 업체인지 확인한다  고소 : 완료일이 없는 경우 기소중지 : JUDG_CD = 2 구약식 : JUDG_CD = 4 그외 고소 아님
    public DOBJ CALLindtn_rept_save2_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtn_rept_save2_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtn_rept_save2_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(COMPN_DAY,  NULL,  1,  DECODE(JUDG_CD,  2,  1,  4,  1,  0))  ACCU_CNT  ,  JUDG_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  COMPN_DAY  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ORDER  BY  ACCU_DAY  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 개별 채권 입금 저장
    // 업소별로 입금 정보를 저장한다
    public DOBJ CALLindtn_rept_save2_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP1");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT","ADJ_AMT","ADJ_GBN"};
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
        String   spname ="GIBU.SP_PROC_REPT_INDTN2";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
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
        robj.setRetcode(1);
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 오류내역 조회
    public DOBJ CALLindtn_rept_save2_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtn_rept_save2_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtn_rept_save2_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  UPSO_CD  ,  START_YRMN  ,  END_YRMN  ,  REPT_AMT  ,  COMIS  ,  RECV_DAY  ,  ACCN_NUM  ,  ERR_GBN  ,  ERR_CTENT  FROM  GIBU.TBRA_REPT_ERR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 개별 입금
    // 업소별로 입금 정보를 저장한다
    public DOBJ CALLindtn_rept_save2_OSP2(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP2");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP2");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT","ADJ_AMT","ADJ_GBN"};
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
        String   spname ="GIBU.SP_PROC_REPT_INDTN2";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
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
        robj.setName("OSP2");
        robj.setRetcode(1);
        robj.Println("OSP2");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 고소 개별 입금
    // 업소별로 입금 정보를 저장한다
    public DOBJ CALLindtn_rept_save2_OSP3(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP3");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP3");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT","ADJ_AMT","ADJ_GBN"};
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
        String   spname ="GIBU.SP_PROC_REPT_INDTN_ACCU2";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
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
        robj.setName("OSP3");
        robj.setRetcode(1);
        robj.Println("OSP3");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 고소 입금 확인
    public DOBJ CALLindtn_rept_save2_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtn_rept_save2_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtn_rept_save2_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //입금 번호
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 개별 채권 입금 삭제
    // 업소별로 입금 정보를 삭제한다
    public DOBJ CALLindtn_rept_save2_OSP4(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP4");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP4");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT","ADJ_AMT","ADJ_GBN"};
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
        String   spname ="GIBU.SP_PROC_REPT_INDTN2";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
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
        robj.setName("OSP4");
        robj.setRetcode(1);
        robj.Println("OSP4");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 개별 입금 삭제
    // 업소별로 입금 정보를 삭제한다
    public DOBJ CALLindtn_rept_save2_OSP5(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP5");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP5");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT","ADJ_AMT","ADJ_GBN"};
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
        String   spname ="GIBU.SP_PROC_REPT_INDTN2";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
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
        robj.setName("OSP5");
        robj.setRetcode(1);
        robj.Println("OSP5");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 개별 고소 입금 삭제
    // 업소별로 입금 정보를 삭제한다
    public DOBJ CALLindtn_rept_save2_OSP11(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP11");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP11");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT","ADJ_AMT","ADJ_GBN"};
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
        String   spname ="GIBU.SP_PROC_REPT_INDTN_ACCU2";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
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
        robj.setName("OSP11");
        robj.setRetcode(1);
        robj.Println("OSP11");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$indtn_rept_save2
    //##**$$rept_detail_select
    /*
    */
    public DOBJ CTLrept_detail_select(DOBJ dobj)
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
            dobj  = CALLrept_detail_select_SEL1(Conn, dobj);           //  업소 정보 조회
            dobj  = CALLrept_detail_select_SEL2(Conn, dobj);           //  원장정보 조회
            if(!dobj.getRetObject("SEL2").getRecord().get("DISTR_GBN").equals(""))
            {
                if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("22"))
                {
                    dobj  = CALLrept_detail_select_SEL27(Conn, dobj);           //  고소 청구정보
                    dobj  = CALLrept_detail_select_SEL28(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
                }
                else if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("06"))
                {
                    dobj  = CALLrept_detail_select_SEL29(Conn, dobj);           //  채권의뢰
                    dobj  = CALLrept_detail_select_SEL30(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
                }
                else
                {
                    dobj  = CALLrept_detail_select_SEL9(Conn, dobj);           //  분배 청구정보
                    dobj  = CALLrept_detail_select_SEL13(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
                }
            }
            else
            {
                if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("22"))
                {
                    dobj  = CALLrept_detail_select_SEL4(Conn, dobj);           //  고소 청구정보
                    dobj  = CALLrept_detail_select_SEL12(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
                }
                else if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("06"))
                {
                    dobj  = CALLrept_detail_select_SEL8(Conn, dobj);           //  채권의뢰
                    dobj  = CALLrept_detail_select_SEL19(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
                }
                else if( dobj.getRetObject("S").getRecord().get("REPT_GBN").equals("01"))
                {
                    dobj  = CALLrept_detail_select_SEL6(Conn, dobj);           //  자동이체 청구정보
                    dobj  = CALLrept_detail_select_SEL11(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
                }
                else
                {
                    dobj  = CALLrept_detail_select_SEL10(Conn, dobj);           //  그외 청구정보
                    dobj  = CALLrept_detail_select_SEL14(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
                }
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
    public DOBJ CTLrept_detail_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_detail_select_SEL1(Conn, dobj);           //  업소 정보 조회
        dobj  = CALLrept_detail_select_SEL2(Conn, dobj);           //  원장정보 조회
        if(!dobj.getRetObject("SEL2").getRecord().get("DISTR_GBN").equals(""))
        {
            if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("22"))
            {
                dobj  = CALLrept_detail_select_SEL27(Conn, dobj);           //  고소 청구정보
                dobj  = CALLrept_detail_select_SEL28(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
            }
            else if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("06"))
            {
                dobj  = CALLrept_detail_select_SEL29(Conn, dobj);           //  채권의뢰
                dobj  = CALLrept_detail_select_SEL30(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
            }
            else
            {
                dobj  = CALLrept_detail_select_SEL9(Conn, dobj);           //  분배 청구정보
                dobj  = CALLrept_detail_select_SEL13(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
            }
        }
        else
        {
            if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("22"))
            {
                dobj  = CALLrept_detail_select_SEL4(Conn, dobj);           //  고소 청구정보
                dobj  = CALLrept_detail_select_SEL12(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
            }
            else if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("06"))
            {
                dobj  = CALLrept_detail_select_SEL8(Conn, dobj);           //  채권의뢰
                dobj  = CALLrept_detail_select_SEL19(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
            }
            else if( dobj.getRetObject("S").getRecord().get("REPT_GBN").equals("01"))
            {
                dobj  = CALLrept_detail_select_SEL6(Conn, dobj);           //  자동이체 청구정보
                dobj  = CALLrept_detail_select_SEL11(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
            }
            else
            {
                dobj  = CALLrept_detail_select_SEL10(Conn, dobj);           //  그외 청구정보
                dobj  = CALLrept_detail_select_SEL14(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select_MRG2( dobj);        //  청구결과 취합
            }
        }
        return dobj;
    }
    // 업소 정보 조회
    public DOBJ CALLrept_detail_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.MONPRNCFEE  ,  B.BSTYP_CD  ,  B.UPSO_GRAD  ,  B.GRAD  ,  C.GRADNM  ,  A.MNGEMSTR_NM  ,  A.CLSBS_YRMN  ,  A.STAFF_CD  ,  E.HAN_NM  STAFF_NM  ,  A.UPSO_CD,  A.UPSO_NM  ,  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,  8)  LAST_YRMN  ,  NVL(D.BALANCE,  0)  BALANCE  ,  NVL(F.ACCU_CNT,  0)  ACCU_CNT  ,  F.JUDG_CD  ,  A.BRAN_CD  ,  G.CLAIM_CNT  ,  A.SONG_STUDENT  ,  A.AERO_STUDENT  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  GRAD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  CHG_DAY  DESC,  CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  ,  (   \n";
        query +=" SELECT  :UPSO_CD  UPSO_CD  ,  NVL(SUM(BALANCE),  0)  BALANCE  FROM  (   \n";
        query +=" SELECT  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  WHERE  ROWNUM  =  1  )  D  ,  INSA.TINS_MST01  E  ,  (   \n";
        query +=" SELECT  DECODE(COMPN_DAY,  NULL,  1,  DECODE(JUDG_CD,  2,  1,  4,  1,  0))  ACCU_CNT  ,  JUDG_CD  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  COMPN_DAY  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ORDER  BY  ACCU_DAY  DESC  )  WHERE  ROWNUM  =  1  )  F  ,  (   \n";
        query +=" SELECT  COUNT(UPSO_CD)  CLAIM_CNT  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  G  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  C.GRAD_GBN  =  B.UPSO_GRAD   \n";
        query +=" AND  D.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  F.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  E.STAFF_NUM(+)  =  A.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 원장정보 조회
    public DOBJ CALLrept_detail_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //원장 년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  MAX(A.DEMD_YRMN)  DEMD_YRMN  ,  B.DISTR_GBN  ,  A.DISTR_SEQ  ,  A.ACCU_GBN  FROM  GIBU.TBRA_NOTE  A  ,  GIBU.TBRA_REPT  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  A.NOTE_NUM  =  '0001'   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  A.REPT_GBN  GROUP  BY  A.UPSO_CD,  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN,  A.ACCU_GBN,  B.DISTR_GBN,  A.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //원장 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 고소 청구정보
    public DOBJ CALLrept_detail_select_SEL27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL27");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL27");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL27(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL27");
        rvobj.Println("SEL27");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL27(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dobj.getRetObject("SEL2").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        String   DISTR_SEQ = dobj.getRetObject("SEL2").getRecord().get("DISTR_SEQ");   //분배 순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.SOL_ORG_AMT  TOT_USE_AMT  ,  C.SOL_ADDT_AMT  TOT_ADDT_AMT  ,  C.SOL_ORG_AMT  +  C.SOL_ADDT_AMT  TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  C.COMPN_DAY  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  B.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  A  ,  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XA.DISTR_GBN  ,  XA.DISTR_SEQ  ,  XA.DISTR_AMT  AS  REPT_AMT  ,  XB.RECV_DAY  ,  XB.COMIS  ,  XB.BANK_CD  ,  XB.ACCN_NUM  ,  XA.REMAK  ,  XB.MAPPING_DAY  FROM  GIBU.TBRA_REPT_UPSO_DISTR  XA  ,  GIBU.TBRA_REPT  XB  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  COMPN_DAY  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  A.DISTR_SEQ(+)  =  B.DISTR_SEQ   \n";
        query +=" AND  C.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.DISTR_SEQ(+)  =  B.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //분배 순번
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select_SEL28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL28");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL28");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL28(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL28");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.MONPRNCFEE  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XA.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 입금결과 취합
    public DOBJ CALLrept_detail_select_MRG1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL4, SEL6, SEL8, SEL9, SEL10, SEL27, SEL29","");
        rvobj.setName("MRG1") ;
        rvobj.Println("MRG1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 청구결과 취합
    public DOBJ CALLrept_detail_select_MRG2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL11, SEL12, SEL13, SEL14, SEL19, SEL28, SEL30","");
        rvobj.setName("MRG2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 채권의뢰
    public DOBJ CALLrept_detail_select_SEL29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL29");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL29");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL29(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL29");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL29(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        String   DISTR_SEQ = dobj.getRetObject("SEL2").getRecord().get("DISTR_SEQ");   //분배 순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.SOL_AMT  TOT_USE_AMT  ,  0  TOT_ADDT_AMT  ,  C.SOL_AMT  TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  ''  AS  COMPN_DAY  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  B.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  A  ,  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XA.DISTR_GBN  ,  XA.DISTR_SEQ  ,  XA.DISTR_AMT  AS  REPT_AMT  ,  XB.RECV_DAY  ,  XB.COMIS  ,  XB.BANK_CD  ,  XB.ACCN_NUM  ,  XA.REMAK  ,  XB.MAPPING_DAY  FROM  GIBU.TBRA_REPT_UPSO_DISTR  XA  ,  GIBU.TBRA_REPT  XB  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  SOL_AMT  ,  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  A.DISTR_SEQ(+)  =  B.DISTR_SEQ   \n";
        query +=" AND  C.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.DISTR_SEQ(+)  =  B.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //분배 순번
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select_SEL30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL30");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL30");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL30(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL30");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL30(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XA.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 분배 청구정보
    public DOBJ CALLrept_detail_select_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dobj.getRetObject("SEL2").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        String   DISTR_SEQ = dobj.getRetObject("SEL2").getRecord().get("DISTR_SEQ");   //분배 순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  NULL  TOT_USE_AMT  ,  NULL  TOT_ADDT_AMT  ,  NULL  TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.DISTR_AMT  AS  REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  C.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  ''  AS  COMPN_DAY  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  B.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  A  ,  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XA.DISTR_GBN  ,  XA.DISTR_SEQ  ,  XA.DISTR_AMT  ,  XB.RECV_DAY  ,  (CASE  WHEN  XA.REPT_GBN  =  '08'   \n";
        query +=" AND  XA.DISTR_SEQ  >  1  THEN  TO_NUMBER('')  ELSE  XB.COMIS  END)  AS  COMIS  ,  XB.BANK_CD  ,  XB.ACCN_NUM  ,  XA.REMAK  ,  XB.MAPPING_DAY  FROM  GIBU.TBRA_REPT_UPSO_DISTR  XA  ,  GIBU.TBRA_REPT  XB  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN  )  B  ,  ACCT.TCAC_BANK  C  WHERE  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  A.DISTR_SEQ(+)  =  B.DISTR_SEQ   \n";
        query +=" AND  C.BANK_CD  (+)  =  B.BANK_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //분배 순번
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XA.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 고소 청구정보
    public DOBJ CALLrept_detail_select_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dobj.getRetObject("SEL2").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.SOL_ORG_AMT  TOT_USE_AMT  ,  C.SOL_ADDT_AMT  TOT_ADDT_AMT  ,  C.SOL_ORG_AMT  +  C.SOL_ADDT_AMT  TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  C.COMPN_DAY  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  B.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  A  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  NULL  DISTR_GBN  ,  NULL  DISTR_SEQ  ,  REPT_AMT  ,  RECV_DAY  ,  COMIS  ,  BANK_CD  ,  ACCN_NUM  ,  REMAK  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  COMPN_DAY  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN(+)  =  B.REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.MONPRNCFEE  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XA.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 채권의뢰
    public DOBJ CALLrept_detail_select_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.SOL_AMT  TOT_USE_AMT  ,  0  TOT_ADDT_AMT  ,  C.SOL_AMT  TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  ''  AS  COMPN_DAY  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  B.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  A  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  NULL  DISTR_GBN  ,  NULL  DISTR_SEQ  ,  REPT_AMT  ,  RECV_DAY  ,  COMIS  ,  BANK_CD  ,  ACCN_NUM  ,  REMAK  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  SOL_AMT  ,  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN(+)  =  B.REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XA.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 자동이체 청구정보
    public DOBJ CALLrept_detail_select_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dobj.getRetObject("SEL2").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.TOT_USE_AMT  ,  C.TOT_ADDT_AMT  ,  C.TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  ''  AS  COMPN_DAY  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  B.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  A  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  NULL  DISTR_GBN  ,  NULL  DISTR_SEQ  ,  REPT_AMT  ,  RECV_DAY  ,  COMIS  ,  BANK_CD  ,  ACCN_NUM  ,  REMAK  ,  UPSO_CD  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  TOT_DEMD_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  DEMD_YRMN  ||  UPSO_CD  =  (   \n";
        query +=" SELECT  MAX(DEMD_YRMN)  ||  :UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.UPSO_CD  (+)  =  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_AUTO  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XA.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 그외 청구정보
    public DOBJ CALLrept_detail_select_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL10(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dobj.getRetObject("SEL2").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.TOT_USE_AMT  ,  C.TOT_ADDT_AMT  ,  C.TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  ''  AS  COMPN_DAY  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  B.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  A  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  NULL  DISTR_GBN  ,  NULL  DISTR_SEQ  ,  REPT_AMT  ,  RECV_DAY  ,  COMIS  ,  BANK_CD  ,  ACCN_NUM  ,  REMAK  ,  UPSO_CD  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  TOT_DEMD_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  ||  UPSO_CD  =  (   \n";
        query +=" SELECT  MAX(DEMD_YRMN)  ||  :UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD(+)  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.UPSO_CD  (+)  =  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select_SEL14(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XA.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$rept_detail_select
    //##**$$rept_init
    /* * 프로그램명 : rept_init
    * 작성자 : 박태현
    * 작성일 : 2009/11/24
    * 설명    : 신규 입력 시 초기화 정보 (업소정보, 원장정보, 청구정보, 청구이력정보) 등을 조회한다.
    Input
    END_YRMN (종료년월)
    START_YRMN (시작년월)
    UPSO_CD (업소 코드)
    YEAR (검색년도)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLrept_init(DOBJ dobj)
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
            dobj  = CALLrept_init_SEL1(Conn, dobj);           //  초기화정보조회
            dobj  = CALLrept_init_SEL2(Conn, dobj);           //  원장조회
            dobj  = CALLrept_init_SEL3(Conn, dobj);           //  청구 요금 계산
            dobj  = CALLrept_init_SEL4(Conn, dobj);           //  청구 이력조회
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
    public DOBJ CTLrept_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_init_SEL1(Conn, dobj);           //  초기화정보조회
        dobj  = CALLrept_init_SEL2(Conn, dobj);           //  원장조회
        dobj  = CALLrept_init_SEL3(Conn, dobj);           //  청구 요금 계산
        dobj  = CALLrept_init_SEL4(Conn, dobj);           //  청구 이력조회
        return dobj;
    }
    // 초기화정보조회
    public DOBJ CALLrept_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_init_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.MONPRNCFEE  ,  B.BSTYP_CD  ,  B.UPSO_GRAD  ,  B.GRAD  ,  C.GRADNM  ,  A.MNGEMSTR_NM  ,  A.CLSBS_YRMN  ,  A.OPBI_DAY  ,  A.STAFF_CD  ,  E.HAN_NM  STAFF_NM  ,  A.UPSO_CD,  A.UPSO_NM  ,  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,  8)  LAST_YRMN  ,  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,  8)  START_YRMN  ,  NVL(D.BALANCE,  0)  BALANCE  ,  NVL(F.ACCU_CNT,  0)  ACCU_CNT  ,  F.JUDG_CD  ,  A.BRAN_CD  ,  G.CLAIM_CNT  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  GRAD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  CHG_DAY  DESC,  CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  ,  (   \n";
        query +=" SELECT  :UPSO_CD  UPSO_CD  ,  NVL(SUM(BALANCE),  0)  BALANCE  FROM  (   \n";
        query +=" SELECT  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  WHERE  ROWNUM  =  1  )  D  ,  INSA.TINS_MST01  E  ,  (   \n";
        query +=" SELECT  DECODE(COMPN_DAY,  NULL,  1,  DECODE(JUDG_CD,  2,  1,  4,  1,  0))  ACCU_CNT  ,  JUDG_CD  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  COMPN_DAY  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ORDER  BY  ACCU_DAY  DESC  )  WHERE  ROWNUM  =  1  )  F  ,  (   \n";
        query +=" SELECT  COUNT(UPSO_CD)  CLAIM_CNT  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  G  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  C.GRAD_GBN  =  B.UPSO_GRAD   \n";
        query +=" AND  D.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  F.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  E.STAFF_NUM(+)  =  A.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 원장조회
    // 조회년도를 기준으로 3개년도 입금 정보를 조회한다.  1. TENV_CODE 에서 입금구분의 명을 가져온다. 이때 입금구분의 HIGH_CD 값은 00147 이다. SQL 에서 HIGH_CD  값은 CODE_CD라는 컬럼명으로 정의해서 값을 설정한다
    public DOBJ CALLrept_init_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_init_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_init_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BB_YEAR ="99";        //재작년
        String B_YEAR ="99";        //작년
        String   BB_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //재작년
        String   B_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //작년
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //검색년도
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  AA.UPSO_CD  BB_UPSO_CD  ,  AA.YRMN  BB_REPT_YRMN  ,  AA.MM  BB_MM  ,  AA.REPT_GBN  BB_REPT_GBN  ,  AA.CODE_NM  BB_CODE_NM  ,  AA.USE_AMT  BB_USE_AMT  ,  AA.REPT_DAY  BB_REPT_DAY  ,  AA.REPT_NUM  BB_REPT_NUM  ,  AA.RECV_DAY  BB_RECV_DAY  ,  AA.ACCU_GBN  BB_ACCU_GBN  ,  AA.DISTR_SEQ  BB_DISTR_SEQ  ,  AA.MAPPING_DAY  BB_MAPPING_DAY  ,  BB.UPSO_CD  B_UPSO_CD  ,  BB.YRMN  B_REPT_YRMN  ,  BB.MM  B_MM  ,  BB.REPT_GBN  B_REPT_GBN  ,  BB.CODE_NM  B_CODE_NM  ,  BB.USE_AMT  B_USE_AMT  ,  BB.REPT_DAY  B_REPT_DAY  ,  BB.REPT_NUM  B_REPT_NUM  ,  BB.RECV_DAY  B_RECV_DAY  ,  BB.ACCU_GBN  B_ACCU_GBN  ,  BB.DISTR_SEQ  B_DISTR_SEQ  ,  BB.MAPPING_DAY  B_MAPPING_DAY  ,  CC.UPSO_CD  ,  CC.YRMN  REPT_YRMN  ,  CC.MM  ,  CC.REPT_GBN  ,  CC.CODE_NM  ,  CC.USE_AMT  ,  CC.REPT_DAY  ,  CC.REPT_NUM  ,  CC.RECV_DAY  ,  CC.ACCU_GBN  ,  CC.DISTR_SEQ  ,  CC.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  '채권',  '22',  DECODE(ACCU_SOLCNT,  1,  '고소해결',  '고소분납'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:BB_YEAR_1)  -  2  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  AA  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  '채권',  '22',  DECODE(ACCU_SOLCNT,  1,  '고소해결',  '고소분납'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:B_YEAR_1)  -  1  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:B_YEAR_1)  -  1  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:B_YEAR_1)  -  1  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  BB  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  '채권',  '22',  DECODE(ACCU_SOLCNT,  1,  '고소해결',  '고소분납'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  :YEAR  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  CC  WHERE  BB.MM  =  AA.MM   \n";
        query +=" AND  CC.MM  =  AA.MM  ORDER  BY  AA.MM ";
        sobj.setSql(query);
        sobj.setString("BB_YEAR_1", BB_YEAR_1);               //재작년
        sobj.setString("B_YEAR_1", B_YEAR_1);               //작년
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 요금 계산
    // 업소코드를 기준으로 가장 최근에 청구된 청구정보를 조회한다 조회된 청구 정보는 UI 화면에서 신규 입금정보 입력 시 시작년월, 종료년월, 금액등을 채우는데 사용된다. 자동이체는 ?고 OCR테이블만 조회
    public DOBJ CALLrept_init_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_init_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_init_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  UPSO_CD  ,  DEMD_YRMN  ,  CLSBS_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  6)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  TOT_ADDT_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_EADDT_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  11)  DSCT_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  ,  (   \n";
        query +=" SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00141'   \n";
        query +=" AND  CODE_CD  =  GIBU.FT_SPLIT(DEMDS,  ',',  6)  )  DEMD_GBN_NM  ,  0  AS  ADJ_AMT  ,  ''  AS  ADJ_GBN  FROM  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  XA.CLSBS_YRMN  ,  XB.END_YRMN  DEMD_YRMN  ,  GIBU.FT_GET_DEMD_AMT2(XA.UPSO_CD,  XB.START_YRMN,  XB.END_YRMN,  XC.DEMD_YRMN,  'O')  DEMDS  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,  6)  START_YRMN  ,  TO_CHAR(SYSDATE,  'YYYYMM')  END_YRMN  FROM  DUAL  )  XB  ,  (   \n";
        query +=" SELECT  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  )  XC  WHERE  XA.UPSO_CD  =  :UPSO_CD  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 이력조회
    // 입금이 완료되지 않은 청구이력정보를 조회한다
    public DOBJ CALLrept_init_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_init_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_init_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.UPSO_CD  ,  XB.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  XC.CODE_NM  AS  DEMD_GBN_NM  ,  XB.MONPRNCFEE  ,  XB.TOT_USE_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_EADDT_AMT  ,  XB.TOT_DEMD_AMT  ,  XB.DEMD_GBN  ,  XB.START_YRMN  ||  '01'  AS  START_YRMN  ,  XB.END_YRMN  ||  '01'  AS  END_YRMN  FROM  (   \n";
        query +=" SELECT  NVL(MAX(NOTE_YRMN),  '190001')  DEMD_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_GBN  IN   \n";
        query +=" (SELECT  CODE_CD  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00141'   \n";
        query +=" AND  CODE_ETC  =  'A')  )  XA  ,  GIBU.TBRA_DEMD_OCR  XB  ,  FIDU.TENV_CODE  XC  WHERE  XB.DEMD_YRMN  >  XA.DEMD_YRMN   \n";
        query +=" AND  XB.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XC.HIGH_CD  =  '00141'   \n";
        query +=" AND  XC.CODE_CD  =  XB.DEMD_GBN  ORDER  BY  XB.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$rept_init
    //##**$$rept_detail
    /*
    */
    public DOBJ CTLrept_detail(DOBJ dobj)
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
            dobj  = CALLrept_detail_SEL1(Conn, dobj);           //  원장조회
            dobj  = CALLrept_detail_SEL2(Conn, dobj);           //  고소정보 조회
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
    public DOBJ CTLrept_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_detail_SEL1(Conn, dobj);           //  원장조회
        dobj  = CALLrept_detail_SEL2(Conn, dobj);           //  고소정보 조회
        return dobj;
    }
    // 원장조회
    // 조회년도를 기준으로 3개년도 입금 정보를 조회한다.  1. TENV_CODE 에서 입금구분의 명을 가져온다. 이때 입금구분의 HIGH_CD 값은 00147 이다. SQL 에서 HIGH_CD  값은 CODE_CD라는 컬럼명으로 정의해서 값을 설정한다
    public DOBJ CALLrept_detail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BB_YEAR ="99";        //재작년
        String B_YEAR ="99";        //작년
        String   BB_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //재작년
        String   B_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //작년
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //검색년도
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  AA.UPSO_CD  BB_UPSO_CD  ,  AA.YRMN  BB_REPT_YRMN  ,  AA.MM  BB_MM  ,  AA.REPT_GBN  BB_REPT_GBN  ,  AA.CODE_NM  BB_CODE_NM  ,  AA.USE_AMT  BB_USE_AMT  ,  AA.REPT_DAY  BB_REPT_DAY  ,  AA.REPT_NUM  BB_REPT_NUM  ,  AA.RECV_DAY  BB_RECV_DAY  ,  AA.ACCU_GBN  BB_ACCU_GBN  ,  AA.DISTR_SEQ  BB_DISTR_SEQ  ,  AA.MAPPING_DAY  BB_MAPPING_DAY  ,  BB.UPSO_CD  B_UPSO_CD  ,  BB.YRMN  B_REPT_YRMN  ,  BB.MM  B_MM  ,  BB.REPT_GBN  B_REPT_GBN  ,  BB.CODE_NM  B_CODE_NM  ,  BB.USE_AMT  B_USE_AMT  ,  BB.REPT_DAY  B_REPT_DAY  ,  BB.REPT_NUM  B_REPT_NUM  ,  BB.RECV_DAY  B_RECV_DAY  ,  BB.ACCU_GBN  B_ACCU_GBN  ,  BB.DISTR_SEQ  B_DISTR_SEQ  ,  BB.MAPPING_DAY  B_MAPPING_DAY  ,  CC.UPSO_CD  ,  CC.YRMN  REPT_YRMN  ,  CC.MM  ,  CC.REPT_GBN  ,  CC.CODE_NM  ,  CC.USE_AMT  ,  CC.REPT_DAY  ,  CC.REPT_NUM  ,  CC.RECV_DAY  ,  CC.ACCU_GBN  ,  CC.DISTR_SEQ  ,  CC.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  '채권',  '22',  DECODE(ACCU_SOLCNT,  1,  '고소해결',  '고소분납'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  WHEN  REPT_GBN  =  '04'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN   \n";
        query +=" AND  JUDG_CD  NOT  IN  ('2',  '3',  '4',  '8')  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:BB_YEAR_1)  -  2  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  AA  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  '채권',  '22',  DECODE(ACCU_SOLCNT,  1,  '고소해결',  '고소분납'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  WHEN  REPT_GBN  =  '04'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN   \n";
        query +=" AND  JUDG_CD  NOT  IN  ('2',  '3',  '4',  '8')  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:B_YEAR_1)  -  1  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:B_YEAR_1)  -  1  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:B_YEAR_1)  -  1  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  BB  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  '채권',  '22',  DECODE(ACCU_SOLCNT,  1,  '고소해결',  '고소분납'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  WHEN  REPT_GBN  =  '04'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN   \n";
        query +=" AND  JUDG_CD  NOT  IN  ('2',  '3',  '4',  '8')  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  :YEAR  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  CC  WHERE  BB.MM  =  AA.MM   \n";
        query +=" AND  CC.MM  =  AA.MM  ORDER  BY  AA.MM ";
        sobj.setSql(query);
        sobj.setString("BB_YEAR_1", BB_YEAR_1);               //재작년
        sobj.setString("B_YEAR_1", B_YEAR_1);               //작년
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 고소정보 조회
    // 고소정보 조회
    public DOBJ CALLrept_detail_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  ACCU_GBN  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  JUDG_CD  FROM  (   \n";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  ACCU_GBN  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  JUDG_CD  ,  (   \n";
        query +=" SELECT  COUNT(YRMN)  FROM  GIBU.COPY_YRMN  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  A.SOL_START_YRMN   \n";
        query +=" AND  A.SOL_END_YRMN  )  DEMD_MMCNT  FROM  GIBU.TBRA_ACCU  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ORDER  BY  ACCU_DAY  DESC,  ACCU_NUM  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$rept_detail
    //##**$$end
}
