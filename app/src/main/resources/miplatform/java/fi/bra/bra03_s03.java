
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s03
{
    public bra03_s03()
    {
    }
    //##**$$ocr_demd_insert
    /*
    */
    public DOBJ CTLocr_data_make(DOBJ dobj)
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
            dobj  = CALLocr_demd_insert_OSP1(Conn, dobj);           //  û�� ������ ����
            dobj  = CALLocr_demd_insert_SEL2(Conn, dobj);           //  û�� ������ ���� ����
            dobj  = CALLocr_demd_insert_SEL3(Conn, dobj);           //  ���κ� û�� �Ϸ� ���� ��ȸ
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
    public DOBJ CTLocr_data_make( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLocr_demd_insert_OSP1(Conn, dobj);           //  û�� ������ ����
        dobj  = CALLocr_demd_insert_SEL2(Conn, dobj);           //  û�� ������ ���� ����
        dobj  = CALLocr_demd_insert_SEL3(Conn, dobj);           //  ���κ� û�� �Ϸ� ���� ��ȸ
        return dobj;
    }
    // û�� ������ ����
    // û�� �����͸� �����ϱ� ���� ���ν��� (GIBU.SP_PROC_DEMDGIRO) �� ȣ���Ѵ�
    public DOBJ CALLocr_demd_insert_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"BRAN_CD","DEMD_YRMN","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_DEMD_OCR";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={"P_CNT_READ","P_CNT_INST","P_CNT_AUTO","P_CNT_PREPAY","P_CNT_ERR"};;
        int[]    outtypes ={12, 12, 12, 12, 12};;
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
    // û�� ������ ���� ����
    // û�� ������ ���� �� �߻��� ���� ����
    public DOBJ CALLocr_demd_insert_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLocr_demd_insert_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLocr_demd_insert_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //û�� ���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_YRMN  ,  C.CODE_NM  DEMD_NM  ,  A.DEMD_GBN  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.ERR_GBN  ,  A.ERR_CTENT  FROM  GIBU.TBRA_DEMD_ERR  A  ,  GIBU.TBRA_UPSO  B  ,  FIDU.TENV_CODE  C  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.CRET_GBN  =  'O'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  A.DEMD_GBN  =  C.CODE_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���κ� û�� �Ϸ� ���� ��ȸ
    // ���κ��� OCR ���������� ��ȸ�Ѵ�
    public DOBJ CALLocr_demd_insert_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLocr_demd_insert_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLocr_demd_insert_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dvobj.getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.GIBU  BRAN_CD  ,  XA.DEPT_NM  ,  DECODE(XB.DEMD_YRMN,  NULL,  NULL,  '�Ϸ�')  BRAN_END  FROM  INSA.TCPM_DEPT  XA  ,  (   \n";
        query +=" SELECT  DISTINCT  B.BRAN_CD  ,  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD  )  XB  WHERE  XA.DEPT_CD  BETWEEN  '106010100'   \n";
        query +=" AND  '106019999'   \n";
        query +=" AND  XA.GIBU  =  XB.BRAN_CD  (+)  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    //##**$$ocr_demd_insert
    //##**$$ocr_demd_init
    /* * ���α׷��� : bra03_s03
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/08
    * ����    : û������ ���κ� ���� û���ڷ� �������θ� ��ȸ�Ѵ�.
    Input
    DEMD_YRMN (DEMD_YRMN)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLocr_data_init(DOBJ dobj)
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
            dobj  = CALLocr_demd_init_SEL1(Conn, dobj);           //  ���κ� û�� �Ϸ� ���� ��ȸ
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
    public DOBJ CTLocr_data_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLocr_demd_init_SEL1(Conn, dobj);           //  ���κ� û�� �Ϸ� ���� ��ȸ
        return dobj;
    }
    // ���κ� û�� �Ϸ� ���� ��ȸ
    // ���κ��� OCR ���������� ��ȸ�Ѵ�
    public DOBJ CALLocr_demd_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLocr_demd_init_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLocr_demd_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dvobj.getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.GIBU  BRAN_CD  ,  XA.DEPT_NM  ,  DECODE(XB.DEMD_YRMN,  NULL,  NULL,  '�Ϸ�')  BRAN_END  ,  XB.INS_DAY  FROM  INSA.TCPM_DEPT  XA  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  MAX(DEMD_YRMN)  DEMD_YRMN  ,  TO_CHAR(MAX(INS_DATE),  'YYYYMMDD')  INS_DAY  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :DEMD_YRMN  GROUP  BY  BRAN_CD  )  XB  WHERE  XA.DEPT_CD  BETWEEN  '106010100'   \n";
        query +=" AND  '106019999'   \n";
        query +=" AND  XA.GIBU  =  XB.BRAN_CD  (+)  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    //##**$$ocr_demd_init
    //##**$$rept_closing
    /*
    */
    public DOBJ CTLrept_closing(DOBJ dobj)
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
            dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  ���κ�����üũ
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
    public DOBJ CTLrept_closing( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  ���κ�����üũ
        return dobj;
    }
    // ���κ�����üũ
    // ���κ� �������̺��� �ش� ��� ������ �ִ��� üũ�Ѵ�
    public DOBJ CALLrept_closing_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_closing_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        String message ="�ش�Ⱓ�� ���������� �����մϴ�. ���������� Ȯ���ϼ���.";
        dobj.setRetmsg(message);
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //û�� ���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  END_YEAR  ||  END_MON  =  :DEMD_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD) ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_closing
    //##**$$end
}
