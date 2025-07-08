
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac03_s01
{
    public tac03_s01()
    {
    }
    //##**$$ttec_incom_save
    /* * ���α׷��� : tac03_s01
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/11/26
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLttec_incom_save(DOBJ dobj)
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
            dobj  = CALLttec_incom_save_MIUD1(Conn, dobj);           //  �ҵ�Ű�MIUD
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
    public DOBJ CTLttec_incom_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttec_incom_save_MIUD1(Conn, dobj);           //  �ҵ�Ű�MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �ҵ�Ű�MIUD
    public DOBJ CALLttec_incom_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttec_incom_save_INS8(Conn, dobj);           //  ����������������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttec_incom_save_UPD9(Conn, dobj);           //  ������������������Ʈ
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttec_incom_save_DEL10(Conn, dobj);           //  DEL
            }
        }
        return dobj;
    }
    // DEL
    public DOBJ CALLttec_incom_save_DEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLttec_incom_save_DEL10(dobj, dvobj);
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
    private SQLObject SQLttec_incom_save_DEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from ACCT.TTAM_INCOME_D  \n";
        query +=" where SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ����������������
    public DOBJ CALLttec_incom_save_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLttec_incom_save_INS8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttec_incom_save_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   NM = dvobj.getRecord().get("NM");   //����
        double   NEED_EXPS = dvobj.getRecord().getDouble("NEED_EXPS");   //�ʿ���
        String   INCOM_GBN = dvobj.getRecord().get("INCOM_GBN");   //�ҵ� ����
        double   INHABTAX = dvobj.getRecord().getDouble("INHABTAX");   //�ֹμ�
        double   SUPP_TOTAMT = dvobj.getRecord().getDouble("SUPP_TOTAMT");   //���� �Ѿ�
        double   INCOMTAX = dvobj.getRecord().getDouble("INCOMTAX");   //�ҵ漼
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   MEM_NO = dvobj.getRecord().get("MEM_NO");   //ȸ����ȣ
        String   NAT_CD = dvobj.getRecord().get("NAT_CD");
        String   BIGO = dvobj.getRecord().get("BIGO");   //������
        String   RESIDENT_GBN = dvobj.getRecord().get("RESIDENT_GBN");   //���ֱ���
        String   RETURN_YRMN = dvobj.getRecord().get("RETURN_YRMN");   //�ͼӳ��
        String   HOUSE_CD = dvobj.getRecord().get("HOUSE_CD");   //������ �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into ACCT.TTAM_INCOME_D (HOUSE_CD, RETURN_YRMN, RESIDENT_GBN, BIGO, NAT_CD, MEM_NO, SEQ, INCOMTAX, SUPP_TOTAMT, INHABTAX, INCOM_GBN, NEED_EXPS, NM)  \n";
        query +=" values(:HOUSE_CD , :RETURN_YRMN , :RESIDENT_GBN , :BIGO , :NAT_CD , :MEM_NO , :SEQ , :INCOMTAX , :SUPP_TOTAMT , :INHABTAX , :INCOM_GBN , :NEED_EXPS , :NM )";
        sobj.setSql(query);
        sobj.setString("NM", NM);               //����
        sobj.setDouble("NEED_EXPS", NEED_EXPS);               //�ʿ���
        sobj.setString("INCOM_GBN", INCOM_GBN);               //�ҵ� ����
        sobj.setDouble("INHABTAX", INHABTAX);               //�ֹμ�
        sobj.setDouble("SUPP_TOTAMT", SUPP_TOTAMT);               //���� �Ѿ�
        sobj.setDouble("INCOMTAX", INCOMTAX);               //�ҵ漼
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("MEM_NO", MEM_NO);               //ȸ����ȣ
        sobj.setString("NAT_CD", NAT_CD);
        sobj.setString("BIGO", BIGO);               //������
        sobj.setString("RESIDENT_GBN", RESIDENT_GBN);               //���ֱ���
        sobj.setString("RETURN_YRMN", RETURN_YRMN);               //�ͼӳ��
        sobj.setString("HOUSE_CD", HOUSE_CD);               //������ �ڵ�
        return sobj;
    }
    // ������������������Ʈ
    public DOBJ CALLttec_incom_save_UPD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLttec_incom_save_UPD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttec_incom_save_UPD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   NM = dvobj.getRecord().get("NM");   //����
        double   NEED_EXPS = dvobj.getRecord().getDouble("NEED_EXPS");   //�ʿ���
        String   INCOM_GBN = dvobj.getRecord().get("INCOM_GBN");   //�ҵ� ����
        double   INHABTAX = dvobj.getRecord().getDouble("INHABTAX");   //�ֹμ�
        double   SUPP_TOTAMT = dvobj.getRecord().getDouble("SUPP_TOTAMT");   //���� �Ѿ�
        double   INCOMTAX = dvobj.getRecord().getDouble("INCOMTAX");   //�ҵ漼
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   MEM_NO = dvobj.getRecord().get("MEM_NO");   //ȸ����ȣ
        String   NAT_CD = dvobj.getRecord().get("NAT_CD");
        String   BIGO = dvobj.getRecord().get("BIGO");   //������
        String   RESIDENT_GBN = dvobj.getRecord().get("RESIDENT_GBN");   //���ֱ���
        String   RETURN_YRMN = dvobj.getRecord().get("RETURN_YRMN");   //�ͼӳ��
        String   HOUSE_CD = dvobj.getRecord().get("HOUSE_CD");   //������ �ڵ�
        String   MOD_STAFF = dobj.getRetObject("G").getRecord().get("USERID");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update ACCT.TTAM_INCOME_D  \n";
        query +=" set HOUSE_CD=:HOUSE_CD , RETURN_YRMN=:RETURN_YRMN , RESIDENT_GBN=:RESIDENT_GBN , MOD_STAFF=:MOD_STAFF , BIGO=:BIGO , NAT_CD=:NAT_CD , MEM_NO=:MEM_NO , INCOMTAX=:INCOMTAX , SUPP_TOTAMT=:SUPP_TOTAMT , INHABTAX=:INHABTAX , INCOM_GBN=:INCOM_GBN , NEED_EXPS=:NEED_EXPS , NM=:NM  \n";
        query +=" where SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("NM", NM);               //����
        sobj.setDouble("NEED_EXPS", NEED_EXPS);               //�ʿ���
        sobj.setString("INCOM_GBN", INCOM_GBN);               //�ҵ� ����
        sobj.setDouble("INHABTAX", INHABTAX);               //�ֹμ�
        sobj.setDouble("SUPP_TOTAMT", SUPP_TOTAMT);               //���� �Ѿ�
        sobj.setDouble("INCOMTAX", INCOMTAX);               //�ҵ漼
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("MEM_NO", MEM_NO);               //ȸ����ȣ
        sobj.setString("NAT_CD", NAT_CD);
        sobj.setString("BIGO", BIGO);               //������
        sobj.setString("RESIDENT_GBN", RESIDENT_GBN);               //���ֱ���
        sobj.setString("RETURN_YRMN", RETURN_YRMN);               //�ͼӳ��
        sobj.setString("HOUSE_CD", HOUSE_CD);               //������ �ڵ�
        sobj.setString("MOD_STAFF", MOD_STAFF);               //���� ���
        return sobj;
    }
    //##**$$ttec_incom_save
    //##**$$ttac_incom_select
    /* * ���α׷��� : tac03_s01
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/11/26
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLttac_incom_select(DOBJ dobj)
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
            dobj  = CALLttac_incom_select_SEL1(Conn, dobj);           //  �ҵ�Ű�����ȸ
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
    public DOBJ CTLttac_incom_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_incom_select_SEL1(Conn, dobj);           //  �ҵ�Ű�����ȸ
        return dobj;
    }
    // �ҵ�Ű�����ȸ
    // �ҵ�Ű����� ��ȸ�Ѵ�.
    public DOBJ CALLttac_incom_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLttac_incom_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_incom_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RETURN_YRMN = dobj.getRetObject("S").getRecord().get("RETURN_YRMN");   //�ͼӳ��
        String   RESIDENT_GBN = dobj.getRetObject("S").getRecord().get("RESIDENT_GBN");   //���ֱ���
        String   MEM_NO = dobj.getRetObject("S").getRecord().get("MEM_NO");   //ȸ����ȣ
        String   INCOM_GBN = dobj.getRetObject("S").getRecord().get("INCOM_GBN");   //�ҵ� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  RETURN_YRMN,  INCOM_GBN,  NM,  MEM_NO,  INCOM_AMT  SUPP_TOTAMT,  NEED_EXPS,  INCOMTAX,  INHABTAX,  RESIDENT_GBN,  HOUSE_CD,  BIGO,  SEQ,  NAT_CD  FROM  ACCT.TTAM_INCOME_D  WHERE  1  =  1   \n";
        query +=" AND  ACCTN_GBN='001'   \n";
        query +=" AND  RETURN_YRMN  =  :RETURN_YRMN   \n";
        query +=" AND  INCOM_GBN  LIKE  :INCOM_GBN||'%'   \n";
        query +=" AND  MEM_NO  LIKE  :MEM_NO||'%'   \n";
        query +=" AND  INCOM_AMT  >  0   \n";
        query +=" AND  RESIDENT_GBN  LIKE  :RESIDENT_GBN||'%'  ORDER  BY  RETURN_YRMN  DESC,  MEM_NO ";
        sobj.setSql(query);
        sobj.setString("RETURN_YRMN", RETURN_YRMN);               //�ͼӳ��
        sobj.setString("RESIDENT_GBN", RESIDENT_GBN);               //���ֱ���
        sobj.setString("MEM_NO", MEM_NO);               //ȸ����ȣ
        sobj.setString("INCOM_GBN", INCOM_GBN);               //�ҵ� ����
        return sobj;
    }
    //##**$$ttac_incom_select
    //##**$$searchCom139
    /* * ���α׷��� : tac03_s01
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/11/26
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLsearchCom139(DOBJ dobj)
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
            dobj  = CALLsearchCom139_SEL1(Conn, dobj);           //  �����ڵ� �ҵ汸�� ��ȸ
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
    public DOBJ CTLsearchCom139( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchCom139_SEL1(Conn, dobj);           //  �����ڵ� �ҵ汸�� ��ȸ
        return dobj;
    }
    // �����ڵ� �ҵ汸�� ��ȸ
    // �ҵ汸��(����ҵ�,��Ÿ�ҵ�)�� ��ȸ
    public DOBJ CALLsearchCom139_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearchCom139_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchCom139_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CODE_CD,CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00139'   \n";
        query +=" AND  CODE_CD  IN('001','002')   \n";
        query +=" AND  USE_YN  ='Y'  ORDER  BY  SORT_CD,CODE_CD,CODE_NM ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$searchCom139
    //##**$$end
}
