
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r17
{
    public bra10_r17()
    {
    }
    //##**$$get_code
    /*
    */
    public DOBJ CTLget_code(DOBJ dobj)
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
            dobj  = CALLget_code_SEL1(Conn, dobj);           //  ��ǥ ���� ��ȸ
            dobj  = CALLget_code_SEL2(Conn, dobj);           //  ��ǥ ���� ��ȸ
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
    public DOBJ CTLget_code( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_code_SEL1(Conn, dobj);           //  ��ǥ ���� ��ȸ
        dobj  = CALLget_code_SEL2(Conn, dobj);           //  ��ǥ ���� ��ȸ
        return dobj;
    }
    // ��ǥ ���� ��ȸ
    // ��ǥ ������ ��ȸ�Ѵ�
    public DOBJ CALLget_code_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_code_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_code_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  'A'  GRAD_GBN  ,  'A  :  ��ü'  GRAD_NM  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  TRIM(GRAD_GBN)  ,  TRIM(GRAD_GBN)  ||  '  :  '  ||  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z' ";
        sobj.setSql(query);
        return sobj;
    }
    // ��ǥ ���� ��ȸ
    // ��ü�� ���� ��
    public DOBJ CALLget_code_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_code_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_code_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  TRIM(GRAD_GBN)  AS  GRAD_GBN  ,  TRIM(GRAD_GBN)  ||  '  :  '  ||  GRADNM  AS  GRAD_NM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z' ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$get_code
    //##**$$select_feerate_histy
    /*
    */
    public DOBJ CTLselect_feerate_histy(DOBJ dobj)
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
            dobj  = CALLselect_feerate_histy_SEL1(Conn, dobj);           //  ��ȸ
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
    public DOBJ CTLselect_feerate_histy( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselect_feerate_histy_SEL1(Conn, dobj);           //  ��ȸ
        return dobj;
    }
    // ��ȸ
    public DOBJ CALLselect_feerate_histy_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLselect_feerate_histy_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_feerate_histy_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.APPL_DAY  ,  A.BSTYP_CD  ,  A.RATE  ,  A.REMAK  ,  A.INSPRES_ID  ,  B.HAN_NM  INSPRES_NM  ,  TO_CHAR(A.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  FROM  GIBU.TBRA_FEERATE_HISTY  A  ,  INSA.TINS_MST01  B  WHERE  A.INSPRES_ID  =  B.STAFF_NUM(+)   \n";
        query +=" AND  A.APPL_DAY  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  A.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  A.BSTYP_CD,  :BSTYP_CD)  ORDER  BY  A.APPL_DAY,  BSTYP_CD ";
        sobj.setSql(query);
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$select_feerate_histy
    //##**$$select_feerate_err
    /*
    */
    public DOBJ CTLselect_feerate_err(DOBJ dobj)
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
            dobj  = CALLselect_feerate_err_SEL1(Conn, dobj);           //  ��ȸ
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
    public DOBJ CTLselect_feerate_err( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselect_feerate_err_SEL1(Conn, dobj);           //  ��ȸ
        return dobj;
    }
    // ��ȸ
    public DOBJ CALLselect_feerate_err_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLselect_feerate_err_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_feerate_err_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_DAY = dobj.getRetObject("S").getRecord().get("APPL_DAY");   //���� ����
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.APPL_DAY  ,  B.BRAN_CD  ,  A.BSTYP_CD  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.REMAK  FROM  GIBU.TBRA_FEERATE_ERR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.APPL_DAY  =  :APPL_DAY   \n";
        query +=" AND  A.BSTYP_CD  =  :BSTYP_CD  ORDER  BY  B.BRAN_CD,  A.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("APPL_DAY", APPL_DAY);               //���� ����
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$select_feerate_err
    //##**$$save_feerate_histy
    /*
    */
    public DOBJ CTLsave_feerate_histy(DOBJ dobj)
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
            dobj  = CALLsave_feerate_histy_MPD7(Conn, dobj);           //  ����
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
    public DOBJ CTLsave_feerate_histy( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_feerate_histy_MPD7(Conn, dobj);           //  ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ����
    public DOBJ CALLsave_feerate_histy_MPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD7");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_feerate_histy_INS6(Conn, dobj);           //  ����
                dobj  = CALLsave_feerate_histy_OSP11(Conn, dobj);           //  ������ ������ ����
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLsave_feerate_histy_INS6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_feerate_histy_INS6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_feerate_histy_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double   RATE = dvobj.getRecord().getDouble("RATE");   //����
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   APPL_DAY = dvobj.getRecord().get("APPL_DAY");   //���� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_FEERATE_HISTY (INS_DATE, APPL_DAY, INSPRES_ID, BSTYP_CD, REMAK, RATE)  \n";
        query +=" values(SYSDATE, :APPL_DAY , :INSPRES_ID , :BSTYP_CD , :REMAK , :RATE )";
        sobj.setSql(query);
        sobj.setDouble("RATE", RATE);               //����
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("APPL_DAY", APPL_DAY);               //���� ����
        return sobj;
    }
    // ������ ������ ����
    public DOBJ CALLsave_feerate_histy_OSP11(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP11");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP11");
        String[]  incolumns ={"APPL_DAY","BSTYP_CD","RATE","INSPRES_ID"};
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
        String   spname ="GIBU.SP_SET_MONPRNCFEE_RATE";
        int[]    intypes={12, 12, 12, 12};;
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
        robj.Println("OSP11");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$save_feerate_histy
    //##**$$end
}
