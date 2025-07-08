
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac09_r02_2
{
    public tac09_r02_2()
    {
    }
    //##**$$check_tran
    /*
    */
    public DOBJ CTLcheck_tran(DOBJ dobj)
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
            dobj  = CALLcheck_tran_SEL1(Conn, dobj);           //  ����������ȸ
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
    public DOBJ CTLcheck_tran( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcheck_tran_SEL1(Conn, dobj);           //  ����������ȸ
        return dobj;
    }
    // ����������ȸ
    public DOBJ CALLcheck_tran_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLcheck_tran_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcheck_tran_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  FILE_CNT  FROM  ACCT.IF_FILE_MAST  WHERE  SUBSTR(FILE_DATE,1,6)  =  :SUPP_YRMN   \n";
        query +=" AND  FILE_NM  LIKE  '%���۱Ƿ�' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    //##**$$check_tran
    //##**$$trans_data
    /*
    */
    public DOBJ CTLtrans_data(DOBJ dobj)
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
            dobj  = CALLtrans_data_OSP1(Conn, dobj);           //  �����������������ν���
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
    public DOBJ CTLtrans_data( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtrans_data_OSP1(Conn, dobj);           //  �����������������ν���
        return dobj;
    }
    // �����������������ν���
    public DOBJ CALLtrans_data_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP1");
        String[]  incolumns ={"GBN","FILE_NM","SUPP_YMD","SUPP_YRMN","INS_STAFF"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INS_STAFF = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //��� ���
            record.put("INS_STAFF",INS_STAFF);
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
        String   spname ="ACCT.SP_IHB_DATA_SEND";
        int[]    intypes={12, 12, 12, 12, 12};;
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
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$trans_data
    //##**$$trans_result
    /*
    */
    public DOBJ CTLtrans_result(DOBJ dobj)
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
            dobj  = CALLtrans_result_SEL1(Conn, dobj);           //  ���ະ����
            dobj  = CALLtrans_result_SEL5(Conn, dobj);           //  �󼼳���
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
    public DOBJ CTLtrans_result( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtrans_result_SEL1(Conn, dobj);           //  ���ະ����
        dobj  = CALLtrans_result_SEL5(Conn, dobj);           //  �󼼳���
        return dobj;
    }
    // ���ະ����
    public DOBJ CALLtrans_result_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtrans_result_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtrans_result_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.IN_BANK_CD  BANK_CD,  NVL  (B.BANK_NM,  '��ȸ��  ��ϵ���  ����  �����ڵ�-'  ||  A.IN_BANK_CD)  BANK_NM,  COUNT  (*)  TOT_CNT,  SUM  (TRAN_AMT)  TOT_AMT,  SUM  (DECODE  (PROC_STS,  '0000',  0,  1))  F_CNT,  SUM  (DECODE  (PROC_STS,  '0000',  0,  TRAN_AMT))  F_AMT,  SUM  (DECODE  (PROC_STS,  '0000',  1,  0))  S_CNT,  SUM  (DECODE  (PROC_STS,  '0000',  TRAN_AMT,  0))  S_AMT  FROM  ACCT.IF_FILE_DETAIL  A,  ACCT.TCAC_BANK  B  WHERE  1  =  1   \n";
        query +=" AND  A.IN_BANK_CD  =  B.BANK_CD(+)   \n";
        query +=" AND  A.OUT_RMK  =  :YRMN  GROUP  BY  A.IN_BANK_CD,  B.BANK_NM  ORDER  BY  A.IN_BANK_CD,  B.BANK_NM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    // �󼼳���
    public DOBJ CALLtrans_result_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtrans_result_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtrans_result_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(FIDU.GET_CODE_NM  (  '00509',  CASE  WHEN  PROC_STS='0000'  THEN  '0000'  WHEN  PROC_STS  <>  '0000'   \n";
        query +=" AND  PROC_STS<>'XXXX'  THEN  A.PROC_STS  WHEN  PROC_STS  ='XXXX'  THEN  A.REF_PROC_STS  END),'��������ȸ  ���')  PROC_STS,  A.TRAN_DATE,  A.OUT_ACCT_NO,  '�뷮��ü'  GBN,  A.IN_BANK_CD,  B.BANK_NM,  A.IN_ACCT_NO,  A.REGI_REF_NM,  A.TRAN_AMT,  CASE  WHEN  PROC_STS  =  '0000'  THEN  A.TRAN_AMT  ELSE  0  END  TOT_AMT,  CASE  WHEN  PROC_STS  <>  '0000'THEN  A.TRAN_AMT  ELSE  0  END  ERR_AMT,  '0'  COMIS,  OUT_RMK,  IN_RMK  FROM  ACCT.IF_FILE_DETAIL  A,  ACCT.TCAC_BANK  B  WHERE  1  =  1   \n";
        query +=" AND  A.IN_BANK_CD  =  B.BANK_CD(+)   \n";
        query +=" AND  A.OUT_RMK  =  :YRMN  ORDER  BY  A.PROC_STS,A.REGI_REF_NM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    //##**$$trans_result
    //##**$$end
}
