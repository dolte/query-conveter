
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac12_s01
{
    public tac12_s01()
    {
    }
    //##**$$dedct_save
    /* * 프로그램명 : tac12_s01
    * 작성자 : 이세준
    * 작성일 : 2009/12/02
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLdedct_save(DOBJ dobj)
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
            dobj  = CALLdedct_save_OSP11(Conn, dobj);           //  회원지급보류해제 및 각종 해제
            dobj  = CALLdedct_save_OSP2(Conn, dobj);           //  매체별관리수수료공제
            dobj  = CALLdedct_save_OSP3(Conn, dobj);           //  실지급공제
            dobj  = CALLdedct_save_OSP6(Conn, dobj);           //  환수금공제
            dobj  = CALLdedct_save_OSP7(Conn, dobj);           //  기타공제
            dobj  = CALLdedct_save_OSP9(Conn, dobj);           //  회비공제
            dobj  = CALLdedct_save_OSP12(Conn, dobj);           //  질권해제
            dobj  = CALLdedct_save_OSP10(Conn, dobj);           //  채권채무변제
            dobj  = CALLdedct_save_UNI11(Conn, dobj);           //  월공제여부저장
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
    public DOBJ CTLdedct_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdedct_save_OSP11(Conn, dobj);           //  회원지급보류해제 및 각종 해제
        dobj  = CALLdedct_save_OSP2(Conn, dobj);           //  매체별관리수수료공제
        dobj  = CALLdedct_save_OSP3(Conn, dobj);           //  실지급공제
        dobj  = CALLdedct_save_OSP6(Conn, dobj);           //  환수금공제
        dobj  = CALLdedct_save_OSP7(Conn, dobj);           //  기타공제
        dobj  = CALLdedct_save_OSP9(Conn, dobj);           //  회비공제
        dobj  = CALLdedct_save_OSP12(Conn, dobj);           //  질권해제
        dobj  = CALLdedct_save_OSP10(Conn, dobj);           //  채권채무변제
        dobj  = CALLdedct_save_UNI11(Conn, dobj);           //  월공제여부저장
        return dobj;
    }
    // 회원지급보류해제 및 각종 해제
    public DOBJ CALLdedct_save_OSP11(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP11");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP11");
        String[]  incolumns ={"SUPP_YRMN","SUPP_DAY"};
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
        String   spname ="FIDU.SP_DEDCT_MDMCLASSSUPPAMT";
        int[]    intypes={12, 12};;
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
        robj.setName("OSP11");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 매체별관리수수료공제
    public DOBJ CALLdedct_save_OSP2(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP2");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP2");
        String[]  incolumns ={"SUPP_YRMN"};
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
        String   spname ="FIDU.SP_DEDCT_MNGCOMISAMT";
        int[]    intypes={12};;
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
        robj.setName("OSP2");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 실지급공제
    public DOBJ CALLdedct_save_OSP3(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP3");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP3");
        String[]  incolumns ={"SUPP_YRMN"};
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
        String   spname ="FIDU.SP_DEDCT_REALSUPPAMT";
        int[]    intypes={12};;
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
        robj.setName("OSP3");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 환수금공제
    public DOBJ CALLdedct_save_OSP6(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP6");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP6");
        String[]  incolumns ={"SUPP_YRMN"};
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
        String   spname ="FIDU.SP_DEDCT_REDEMAMT";
        int[]    intypes={12};;
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
        robj.setName("OSP6");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 기타공제
    public DOBJ CALLdedct_save_OSP7(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP7");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP7");
        String[]  incolumns ={"SUPP_YRMN"};
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
        String   spname ="FIDU.SP_DEDCT_ETCAMT";
        int[]    intypes={12};;
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
        robj.setName("OSP7");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 회비공제
    public DOBJ CALLdedct_save_OSP9(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP9");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP9");
        String[]  incolumns ={"SUPP_YRMN"};
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
        String   spname ="FIDU.SP_DEDCT_CLUBEXPSAMT";
        int[]    intypes={12};;
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
        robj.setName("OSP9");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 질권해제
    public DOBJ CALLdedct_save_OSP12(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP12");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP12");
        String[]  incolumns ={"SUPP_YRMN","SUPP_DAY"};
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
        String   spname ="FIDU.SP_DEDCT_PLEDAMT";
        int[]    intypes={12, 12};;
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
        robj.setName("OSP12");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 채권채무변제
    public DOBJ CALLdedct_save_OSP10(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP10");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP10");
        String[]  incolumns ={"SUPP_YRMN","SUPP_DAY"};
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
        String   spname ="FIDU.SP_DEDCT_CLAIMDEBTAMT";
        int[]    intypes={12, 12};;
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
        robj.setName("OSP10");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 월공제여부저장
    public DOBJ CALLdedct_save_UNI11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("UNI11");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdedct_save_UNI11UPD(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLdedct_save_UNI11INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI11") ;
        rvobj.setRetcode(1);
        rvobj.Println("UNI11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdedct_save_UNI11UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String TRUSTACCTNDEDCT_DATE ="";        //신탁회계공제일자
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        String   TRUSTACCTNDEDCT_YN ="1";   //신탁회계공제여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_ACCTNEND  \n";
        query +=" set TRUSTACCTNDEDCT_YN=:TRUSTACCTNDEDCT_YN , TRUSTACCTNDEDCT_DATE=SYSDATE  \n";
        query +=" where SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("TRUSTACCTNDEDCT_YN", TRUSTACCTNDEDCT_YN);               //신탁회계공제여부
        return sobj;
    }
    private SQLObject SQLdedct_save_UNI11INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String TRUSTACCTNDEDCT_DATE ="";        //신탁회계공제일자
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        String   TRUSTACCTNDEDCT_YN ="1";   //신탁회계공제여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_ACCTNEND (TRUSTACCTNDEDCT_YN, SUPP_YRMN, TRUSTACCTNDEDCT_DATE)  \n";
        query +=" values(:TRUSTACCTNDEDCT_YN , :SUPP_YRMN , SYSDATE)";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("TRUSTACCTNDEDCT_YN", TRUSTACCTNDEDCT_YN);               //신탁회계공제여부
        return sobj;
    }
    //##**$$dedct_save
    //##**$$dedct_cancel
    /* * 프로그램명 : tac12_s01
    * 작성자 : 이세준
    * 작성일 : 2009/12/02
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLdedct_cancel(DOBJ dobj)
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
            dobj  = CALLdedct_cancel_UNI2(Conn, dobj);           //  월공제취소
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
    public DOBJ CTLdedct_cancel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdedct_cancel_UNI2(Conn, dobj);           //  월공제취소
        return dobj;
    }
    // 월공제취소
    public DOBJ CALLdedct_cancel_UNI2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("UNI2");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdedct_cancel_UNI2UPD(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLdedct_cancel_UNI2INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI2") ;
        rvobj.setRetcode(1);
        rvobj.Println("UNI2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdedct_cancel_UNI2UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String TRUSTACCTNDEDCT_DATE ="";        //신탁회계공제일자
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        String   TRUSTACCTNDEDCT_YN ="0";   //신탁회계공제여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_ACCTNEND  \n";
        query +=" set TRUSTACCTNDEDCT_YN=:TRUSTACCTNDEDCT_YN , TRUSTACCTNDEDCT_DATE=SYSDATE  \n";
        query +=" where SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("TRUSTACCTNDEDCT_YN", TRUSTACCTNDEDCT_YN);               //신탁회계공제여부
        return sobj;
    }
    private SQLObject SQLdedct_cancel_UNI2INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String TRUSTACCTNDEDCT_DATE ="";        //신탁회계공제일자
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        String   TRUSTACCTNDEDCT_YN ="0";   //신탁회계공제여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_ACCTNEND (TRUSTACCTNDEDCT_YN, SUPP_YRMN, TRUSTACCTNDEDCT_DATE)  \n";
        query +=" values(:TRUSTACCTNDEDCT_YN , :SUPP_YRMN , SYSDATE)";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("TRUSTACCTNDEDCT_YN", TRUSTACCTNDEDCT_YN);               //신탁회계공제여부
        return sobj;
    }
    //##**$$dedct_cancel
    //##**$$dedct_yn
    /* * 프로그램명 : tac12_s01
    * 작성자 : 이세준
    * 작성일 : 2009/11/17
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLdedct_yn(DOBJ dobj)
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
            dobj  = CALLdedct_yn_SEL1(Conn, dobj);           //  저작권사용료조회
            dobj  = CALLdedct_yn_SEL2(Conn, dobj);           //  수수료율누락분 체크
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
    public DOBJ CTLdedct_yn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdedct_yn_SEL1(Conn, dobj);           //  저작권사용료조회
        dobj  = CALLdedct_yn_SEL2(Conn, dobj);           //  수수료율누락분 체크
        return dobj;
    }
    // 저작권사용료조회
    public DOBJ CALLdedct_yn_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLdedct_yn_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdedct_yn_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN_START = dobj.getRetObject("S").getRecord().get("SUPP_YRMN_START");   //조회시작
        String   SUPP_YRMN_END = dobj.getRetObject("S").getRecord().get("SUPP_YRMN_END");   //조회종료
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUPP_YRMN,  TRUSTACCTNDEDCT_YN  AS  END_YN,  TO_CHAR(TRUSTACCTNDEDCT_DATE,  'YYYY/MM/DD  HH24:MI:SS')  AS  END_DATE  FROM  FIDU.TTAC_ACCTNEND  WHERE  1  =  1   \n";
        query +=" AND  SUPP_YRMN  BETWEEN  :SUPP_YRMN_START   \n";
        query +=" AND  :SUPP_YRMN_END  ORDER  BY  SUPP_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN_START", SUPP_YRMN_START);               //조회시작
        sobj.setString("SUPP_YRMN_END", SUPP_YRMN_END);               //조회종료
        return sobj;
    }
    // 수수료율누락분 체크
    public DOBJ CALLdedct_yn_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLdedct_yn_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdedct_yn_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN_END = dobj.getRetObject("S").getRecord().get("SUPP_YRMN_END");   //조회종료
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MDM_CD,  LARGECLASS_CD_NM,  SCLASS_CD_NM,  MDM_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD  IN  (   \n";
        query +=" SELECT  MDM_CD  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =:SUPP_YRMN_END  MINUS   \n";
        query +=" SELECT  MDM_CD  FROM  FIDU.TENV_MNGCOMIS  WHERE  APPL_YRMN  =:SUPP_YRMN_END  UNION  ALL   \n";
        query +=" SELECT  MDM_CD  FROM  FIDU.TENV_MNGCOMIS  WHERE  MNGCOMIS_RATE  =0   \n";
        query +=" AND  APPL_YRMN  =:SUPP_YRMN_END  ) ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN_END", SUPP_YRMN_END);               //조회종료
        return sobj;
    }
    //##**$$dedct_yn
    //##**$$dedct_select
    /* * 프로그램명 : tac12_s01
    * 작성자 : 권순길
    * 작성일 : 2009/10/15
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLdedct_select(DOBJ dobj)
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
            dobj  = CALLdedct_select_SEL1(Conn, dobj);           //  저작권사용료조회
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
    public DOBJ CTLdedct_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdedct_select_SEL1(Conn, dobj);           //  저작권사용료조회
        return dobj;
    }
    // 저작권사용료조회
    public DOBJ CALLdedct_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLdedct_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdedct_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CP_SUPP_YRMN  AS  SUPP_YRMN,  CP_MB_CD  AS  MB_CD,  CP_MB_NM  AS  MB_NM,  CP_TRNSF_GBN  AS  TRNSF_GBN,  CP_TRNSF_GBN_NM  AS  TRNSF_GBN_NM,  CP_TOT_ORGDISTR_AMT  AS  TOT_ORGDISTR_AMT,  CP_TOT_REALSUPP_AMT  AS  TOT_REALSUPP_AMT,  CP_TOT_DEDCT_AMT  AS  TOT_DEDCT_AMT,   \n";
        query +=" (SELECT  DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  WHERE  SUPP_YRMN  =  CP_SUPP_YRMN   \n";
        query +=" AND  MB_CD  =  CP_MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  CP_TRNSF_GBN   \n";
        query +=" AND  DEDCT_GBNONE  =  '01'   \n";
        query +=" AND  DEDCT_GBNTWO  =  '01'  )  AS  MNGCOMIS_AMT,   \n";
        query +=" (SELECT  DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  WHERE  SUPP_YRMN  =  CP_SUPP_YRMN   \n";
        query +=" AND  MB_CD  =  CP_MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  CP_TRNSF_GBN   \n";
        query +=" AND  DEDCT_GBNONE  =  '01'   \n";
        query +=" AND  DEDCT_GBNTWO  =  '02'  )  AS  INCOMTAX_AMT,   \n";
        query +=" (SELECT  DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  WHERE  SUPP_YRMN  =  CP_SUPP_YRMN   \n";
        query +=" AND  MB_CD  =  CP_MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  CP_TRNSF_GBN   \n";
        query +=" AND  DEDCT_GBNONE  =  '01'   \n";
        query +=" AND  DEDCT_GBNTWO  =  '03'  )  AS  INHABTAX_AMT,  CP_TOT_ATAX_AMT  AS  TOT_ATAX_AMT,  CP_TOT_SUSP_RELS_AMT  AS  TOT_SUSP_RELS_AMT,  CP_TOT_REPAY_AMT  AS  TOT_REPAY_AMT,  CP_REMAK_CTENT  AS  REMAK_CTENT  FROM  (   \n";
        query +=" SELECT  SUPP_YRMN  AS  CP_SUPP_YRMN,  MB_CD  AS  CP_MB_CD,  FIDU.GET_MB_NM(MB_CD)  AS  CP_MB_NM,  TRNSF_GBN  AS  CP_TRNSF_GBN,  DECODE(TRNSF_GBN,  '1',  '일반',  '양도')  AS  CP_TRNSF_GBN_NM,  TOT_ORGDISTR_AMT  AS  CP_TOT_ORGDISTR_AMT,  TOT_REALSUPP_AMT  AS  CP_TOT_REALSUPP_AMT,  TOT_DEDCT_AMT  AS  CP_TOT_DEDCT_AMT,  TOT_ATAX_AMT  AS  CP_TOT_ATAX_AMT,  TOT_SUSP_RELS_AMT  AS  CP_TOT_SUSP_RELS_AMT,  TOT_REPAY_AMT  AS  CP_TOT_REPAY_AMT,  REMAK_CTENT  AS  CP_REMAK_CTENT  FROM  FIDU.TTAC_COPYRTAL  WHERE  SUPP_YRMN  =  :SUPP_YRMN  ) ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$dedct_select
    //##**$$end
}
