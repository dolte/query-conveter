
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_t19_1
{
    public bra04_t19_1()
    {
    }
    //##**$$misu_sum_select
    /*
    */
    public DOBJ CTLmisu_sum_select(DOBJ dobj)
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
            dobj  = CALLmisu_sum_select_SEL1(Conn, dobj);           //  ��ȸ
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
    public DOBJ CTLmisu_sum_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmisu_sum_select_SEL1(Conn, dobj);           //  ��ȸ
        return dobj;
    }
    // ��ȸ
    public DOBJ CALLmisu_sum_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmisu_sum_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmisu_sum_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NONPY_DAY  ,  SUM(REPT_AMT)  AS  REPT_AMT  ,  SUM(COMIS_AMT)  AS  COMIS_AMT  ,  SUM(RETURN_AMT)  AS  RETURN_AMT  ,  SUM(ADJ_AMT)  AS  ADJ_AMT  ,  SUM(CNT_SATN)  AS  CNT_SATN  ,  SUM(BEFORE_NONPY_AMT)  AS  BEFORE_NONPY_AMT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  ,  CASE  WHEN  SUM(BEFORE_NONPY_AMT)  =  0   \n";
        query +=" AND  SUM(NONPY_AMT)  =  0  THEN  '-'  WHEN  SUM(NONPY_AMT)  =  0  THEN  '��������'  ELSE  '��������'  END  as  BTN_EXECUTE  FROM  (   \n";
        query +=" SELECT  AA.MAPPING_DAY  AS  NONPY_DAY  ,  SUM(NVL(AA.REPT_AMT,0))  AS  REPT_AMT  ,  SUM(NVL(AA.COMIS,0))  AS  COMIS_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  ADJ_AMT  ,  0  AS  CNT_SATN  ,  0  AS  BEFORE_NONPY_AMT  ,  0  AS  NONPY_AMT  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  AA  ,  GIBU.TBRA_UPSO  BB  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  AA.MAPPING_DAY   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(AA.UPSO_CD),1,1)  <>  't'  GROUP  BY  AA.MAPPING_DAY  UNION  ALL   \n";
        query +=" SELECT  A.RETURN_DAY  AS  NONPY_DAY  ,  0  AS  REPT_AMT  ,  0  AS  COMIS_AMT  ,  SUM(RETURN_AMT)  RETURN_AMT  ,  0  AS  ADJ_AMT  ,  0  AS  CNT_SATN  ,  0  AS  BEFORE_NONPY_AMT  ,  0  AS  NONPY_AMT  FROM  GIBU.TBRA_REPT_RETURN  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.RETURN_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,1)  <>  't'  GROUP  BY  A.RETURN_DAY  UNION  ALL   \n";
        query +=" SELECT  NONPY_DAY  ,  0  AS  REPT_AMT  ,  0  AS  COMIS_AMT  ,  0  AS  RETURN_AMT  ,  SUM(ADJ_AMT)  AS  ADJ_AMT  ,  0  AS  CNT_SATN  ,  0  AS  BEFORE_NONPY_AMT  ,  0  AS  NONPY_AMT  FROM  GIBU.TBRA_MISU_ADJ  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.NONPY_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,1)  <>  't'  GROUP  BY  NONPY_DAY  UNION  ALL   \n";
        query +=" SELECT  NONPY_DAY  ,  0  AS  REPT_AMT  ,  0  AS  COMIS_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  ADJ_AMT  ,  SUM(CNT_SATN1  +  CNT_SATN2  +  CNT_SATN3)  AS  CNT_SATN  ,  0  AS  BEFORE_NONPY_AMT  ,  0  AS  NONPY_AMT  FROM  (   \n";
        query +=" SELECT  A.NONPY_DAY  ,  A.UPSO_CD  ,  A.SEQ  ,  A.ADJ_AMT  ,  A.ADJ_GBN  ,  A.SATN1  ,  A.SATN2  ,  A.SATN3  ,  C.SATN1  ,  C.SATN2  ,  C.SATN3  ,  CASE  WHEN  C.SATN1  IS  NOT  NULL   \n";
        query +=" AND  A.SATN1  IS  NULL  THEN  1  ELSE  0  END  CNT_SATN1  ,  CASE  WHEN  C.SATN2  IS  NOT  NULL   \n";
        query +=" AND  A.SATN2  IS  NULL  THEN  1  ELSE  0  END  CNT_SATN2  ,  CASE  WHEN  C.SATN3  IS  NOT  NULL   \n";
        query +=" AND  A.SATN3  IS  NULL  THEN  1  ELSE  0  END  CNT_SATN3  FROM  GIBU.TBRA_MISU_ADJ  A  ,  GIBU.TBRA_UPSO  B  ,  GIBU.TBRA_MISU_ADJ_SATN  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  C.BRAN_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.ADJ_GBN  =  C.ADJ_GBN   \n";
        query +=" AND  A.ADJ_AMT  <>  0   \n";
        query +=" AND  A.NONPY_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,1)  <>  't'   \n";
        query +=" AND  ADJ_AMT  <>  0  )  GROUP  BY  NONPY_DAY  UNION  ALL   \n";
        query +=" SELECT  NONPY_DAY  ,  0  AS  REPT_AMT  ,  0  AS  COMIS_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  ADJ_AMT  ,  0  AS  CNT_SATN  ,  SUM(BEFORE_NONPY_AMT)  AS  BEFORE_NONPY_AMT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  FROM  GIBU.TBRA_MISU  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NONPY_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  <>  't'  GROUP  BY  NONPY_DAY  )  GROUP  BY  NONPY_DAY  ORDER  BY  NONPY_DAY ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    //##**$$misu_sum_select
    //##**$$check_nonpy_amt
    /*
    */
    public DOBJ CTLcheck_nonpy_amt(DOBJ dobj)
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
            dobj  = CALLcheck_nonpy_amt_SEL1(Conn, dobj);           //  ��ȸ
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
    public DOBJ CTLcheck_nonpy_amt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcheck_nonpy_amt_SEL1(Conn, dobj);           //  ��ȸ
        return dobj;
    }
    // ��ȸ
    public DOBJ CALLcheck_nonpy_amt_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLcheck_nonpy_amt_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcheck_nonpy_amt_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NONPY_DAY = dobj.getRetObject("S").getRecord().get("NONPY_DAY");   //�̳�����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  (   \n";
        query +=" SELECT  NVL(SUM(NONPY_AMT),0)  FROM  GIBU.TBRA_MISU  WHERE  NONPY_DAY  =  TO_CHAR(TO_DATE(:NONPY_DAY)-1,  'YYYYMMDD')   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  AS  YESTER_NONPY_AMT,  (   \n";
        query +=" SELECT  NVL(SUM(NONPY_AMT),0)  FROM  GIBU.TBRA_MISU  WHERE  NONPY_DAY  =  TO_CHAR(TO_DATE(:NONPY_DAY)+1,  'YYYYMMDD')   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  AS  TOMORROW_NONPY_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("NONPY_DAY", NONPY_DAY);               //�̳�����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$check_nonpy_amt
    //##**$$insert_misu_chekwon
    /*
    */
    public DOBJ CTLinsert_misu_chekwon(DOBJ dobj)
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
            dobj  = CALLinsert_misu_chekwon_OSP1(Conn, dobj);           //  ���ν�����
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
    public DOBJ CTLinsert_misu_chekwon( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLinsert_misu_chekwon_OSP1(Conn, dobj);           //  ���ν�����
        return dobj;
    }
    // ���ν�����
    public DOBJ CALLinsert_misu_chekwon_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"BRAN_CD","NONPY_DAY"};
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
        String   spname ="GIBU.SP_MISU_INSERT_ALL";
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
        robj.setName("OSP1");
        robj.setRetcode(1);
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$insert_misu_chekwon
    //##**$$delete_misu_chekwon
    /*
    */
    public DOBJ CTLdelete_misu_chekwon(DOBJ dobj)
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
            dobj  = CALLdelete_misu_chekwon_OSP1(Conn, dobj);           //  ���ν�����
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
    public DOBJ CTLdelete_misu_chekwon( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdelete_misu_chekwon_OSP1(Conn, dobj);           //  ���ν�����
        return dobj;
    }
    // ���ν�����
    public DOBJ CALLdelete_misu_chekwon_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"BRAN_CD","NONPY_DAY"};
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
        String   spname ="GIBU.SP_MISU_DELETE";
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
        robj.setName("OSP1");
        robj.setRetcode(1);
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$delete_misu_chekwon
    //##**$$end
}
