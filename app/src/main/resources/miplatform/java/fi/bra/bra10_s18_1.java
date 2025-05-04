
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s18_1
{
    public bra10_s18_1()
    {
    }
    //##**$$stomu_save
    /*
    */
    public DOBJ CTLstomu_save(DOBJ dobj)
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
            dobj  = CALLstomu_save_XIUD17(Conn, dobj);           //  ���� ���̺� �ʱ�ȭ
            dobj  = CALLstomu_save_MIUD1(Conn, dobj);           //  MIUD �б�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLstomu_save_SEL16(Conn, dobj);           //  Ÿ��ü�ߺ�����
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
    public DOBJ CTLstomu_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstomu_save_XIUD17(Conn, dobj);           //  ���� ���̺� �ʱ�ȭ
        dobj  = CALLstomu_save_MIUD1(Conn, dobj);           //  MIUD �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLstomu_save_SEL16(Conn, dobj);           //  Ÿ��ü�ߺ�����
        return dobj;
    }
    // ���� ���̺� �ʱ�ȭ
    public DOBJ CALLstomu_save_XIUD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD17");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("XIUD17");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_XIUD17(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_XIUD17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" TRUNCATE  TABLE  GIBU.TBRA_STOMU_ERR ";
        sobj.setSql(query);
        return sobj;
    }
    // MIUD �б�
    public DOBJ CALLstomu_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MIUD1");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLstomu_save_SEL30(Conn, dobj);           //  �������ε忩��
                if(!dobj.getRetObject("SEL30").getRecord().get("GBN").equals(""))
                {
                    dobj  = CALLstomu_save_XIUD28(Conn, dobj);           //  �ű��Է�
                }
                else
                {
                    dobj  = CALLstomu_save_SEL32(Conn, dobj);           //  Ÿ��ü�������
                    if( dobj.getRetObject("SEL32").getRecord().getDouble("CNT") > 0)
                    {
                        dobj  = CALLstomu_save_INS34(Conn, dobj);           //  ���� ������ ����
                    }
                    else
                    {
                        dobj  = CALLstomu_save_XIUD40(Conn, dobj);           //  �����Է�
                        dobj  = CALLstomu_save_XIUD36(Conn, dobj);           //  �����̷� �Է�
                        dobj  = CALLstomu_save_UPD38(Conn, dobj);           //  ������� ���ι������
                    }
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLstomu_save_SEL15(Conn, dobj);           //  Ÿ��ü�������
                if( dobj.getRetObject("SEL15").getRecord().getDouble("CNT") > 0)
                {
                    dobj  = CALLstomu_save_INS18(Conn, dobj);           //  ���� ������ ����
                }
                else
                {
                    dobj  = CALLstomu_save_SEL14(Conn, dobj);           //  ���� �����ڵ� Ȯ��
                    if( dobj.getRetObject("SEL14").getRecord().get("UPSO_CD").equals(dobj.getRetObject("R").getRecord().get("UPSO_CD")))
                    {
                        dobj  = CALLstomu_save_SEL19(Conn, dobj);           //  ���� ���Ҹ� Ȯ��
                        if( dobj.getRetObject("SEL19").getRecord().get("BSCON_UPSO_NM").equals(dobj.getRetObject("R").getRecord().get("BSCON_UPSO_NM")))
                        {
                            dobj  = CALLstomu_save_SEL21(Conn, dobj);           //  ���� �����ּ� Ȯ��
                            if( dobj.getRetObject("SEL21").getRecord().get("UPSO_ADDR").equals(dobj.getRetObject("R").getRecord().get("UPSO_ADDR")))
                            {
                                dobj  = CALLstomu_save_SEL24(Conn, dobj);           //  ���� ������ Ȯ��
                                if( dobj.getRetObject("SEL24").getRecord().get("UPSO_BRA").equals(dobj.getRetObject("R").getRecord().get("UPSO_BRA")))
                                {
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                                else
                                {
                                    dobj  = CALLstomu_save_XIUD25(Conn, dobj);           //  �����̷� �Է�
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                            }
                            else
                            {
                                dobj  = CALLstomu_save_XIUD22(Conn, dobj);           //  �����̷� �Է�
                                dobj  = CALLstomu_save_SEL24(Conn, dobj);           //  ���� ������ Ȯ��
                                if( dobj.getRetObject("SEL24").getRecord().get("UPSO_BRA").equals(dobj.getRetObject("R").getRecord().get("UPSO_BRA")))
                                {
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                                else
                                {
                                    dobj  = CALLstomu_save_XIUD25(Conn, dobj);           //  �����̷� �Է�
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                            }
                        }
                        else
                        {
                            dobj  = CALLstomu_save_XIUD20(Conn, dobj);           //  �����̷� �Է�
                            dobj  = CALLstomu_save_SEL21(Conn, dobj);           //  ���� �����ּ� Ȯ��
                            if( dobj.getRetObject("SEL21").getRecord().get("UPSO_ADDR").equals(dobj.getRetObject("R").getRecord().get("UPSO_ADDR")))
                            {
                                dobj  = CALLstomu_save_SEL24(Conn, dobj);           //  ���� ������ Ȯ��
                                if( dobj.getRetObject("SEL24").getRecord().get("UPSO_BRA").equals(dobj.getRetObject("R").getRecord().get("UPSO_BRA")))
                                {
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                                else
                                {
                                    dobj  = CALLstomu_save_XIUD25(Conn, dobj);           //  �����̷� �Է�
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                            }
                            else
                            {
                                dobj  = CALLstomu_save_XIUD22(Conn, dobj);           //  �����̷� �Է�
                                dobj  = CALLstomu_save_SEL24(Conn, dobj);           //  ���� ������ Ȯ��
                                if( dobj.getRetObject("SEL24").getRecord().get("UPSO_BRA").equals(dobj.getRetObject("R").getRecord().get("UPSO_BRA")))
                                {
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                                else
                                {
                                    dobj  = CALLstomu_save_XIUD25(Conn, dobj);           //  �����̷� �Է�
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                            }
                        }
                    }
                    else
                    {
                        dobj  = CALLstomu_save_XIUD10(Conn, dobj);           //  �����̷� �Է�
                        dobj  = CALLstomu_save_UPD17(Conn, dobj);           //  �������� ���������
                        dobj  = CALLstomu_save_UPD18(Conn, dobj);           //  ������� ���ι������
                        dobj  = CALLstomu_save_SEL19(Conn, dobj);           //  ���� ���Ҹ� Ȯ��
                        if( dobj.getRetObject("SEL19").getRecord().get("BSCON_UPSO_NM").equals(dobj.getRetObject("R").getRecord().get("BSCON_UPSO_NM")))
                        {
                            dobj  = CALLstomu_save_SEL21(Conn, dobj);           //  ���� �����ּ� Ȯ��
                            if( dobj.getRetObject("SEL21").getRecord().get("UPSO_ADDR").equals(dobj.getRetObject("R").getRecord().get("UPSO_ADDR")))
                            {
                                dobj  = CALLstomu_save_SEL24(Conn, dobj);           //  ���� ������ Ȯ��
                                if( dobj.getRetObject("SEL24").getRecord().get("UPSO_BRA").equals(dobj.getRetObject("R").getRecord().get("UPSO_BRA")))
                                {
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                                else
                                {
                                    dobj  = CALLstomu_save_XIUD25(Conn, dobj);           //  �����̷� �Է�
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                            }
                            else
                            {
                                dobj  = CALLstomu_save_XIUD22(Conn, dobj);           //  �����̷� �Է�
                                dobj  = CALLstomu_save_SEL24(Conn, dobj);           //  ���� ������ Ȯ��
                                if( dobj.getRetObject("SEL24").getRecord().get("UPSO_BRA").equals(dobj.getRetObject("R").getRecord().get("UPSO_BRA")))
                                {
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                                else
                                {
                                    dobj  = CALLstomu_save_XIUD25(Conn, dobj);           //  �����̷� �Է�
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                            }
                        }
                        else
                        {
                            dobj  = CALLstomu_save_XIUD20(Conn, dobj);           //  �����̷� �Է�
                            dobj  = CALLstomu_save_SEL21(Conn, dobj);           //  ���� �����ּ� Ȯ��
                            if( dobj.getRetObject("SEL21").getRecord().get("UPSO_ADDR").equals(dobj.getRetObject("R").getRecord().get("UPSO_ADDR")))
                            {
                                dobj  = CALLstomu_save_SEL24(Conn, dobj);           //  ���� ������ Ȯ��
                                if( dobj.getRetObject("SEL24").getRecord().get("UPSO_BRA").equals(dobj.getRetObject("R").getRecord().get("UPSO_BRA")))
                                {
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                                else
                                {
                                    dobj  = CALLstomu_save_XIUD25(Conn, dobj);           //  �����̷� �Է�
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                            }
                            else
                            {
                                dobj  = CALLstomu_save_XIUD22(Conn, dobj);           //  �����̷� �Է�
                                dobj  = CALLstomu_save_SEL24(Conn, dobj);           //  ���� ������ Ȯ��
                                if( dobj.getRetObject("SEL24").getRecord().get("UPSO_BRA").equals(dobj.getRetObject("R").getRecord().get("UPSO_BRA")))
                                {
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                                else
                                {
                                    dobj  = CALLstomu_save_XIUD25(Conn, dobj);           //  �����̷� �Է�
                                    dobj  = CALLstomu_save_XIUD11(Conn, dobj);           //  �������ǻ���� ������Ʈ
                                }
                            }
                        }
                    }
                }
            }
        }
        return dobj;
    }
    // �������ε忩��
    public DOBJ CALLstomu_save_SEL30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL30");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL30");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstomu_save_SEL30(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL30");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_SEL30(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("R").getRecord().get("GBN");   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :GBN  AS  GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //����
        return sobj;
    }
    // Ÿ��ü�������
    // Ÿ��ü�� ��������� ���� ����ֱ� ����
    public DOBJ CALLstomu_save_SEL15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL15");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL15");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstomu_save_SEL15(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_SEL15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �ű��Է�
    public DOBJ CALLstomu_save_XIUD28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD28");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_XIUD28(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD28");
        rvobj.Println("XIUD28");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_XIUD28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   BSCON_UPSO_NM = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_NM");   //Ÿ��ü���Ҹ�
        String   BSTYP_CD = dobj.getRetObject("R").getRecord().get("BSTYP_NM");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   UPSO_ADDR = dobj.getRetObject("R").getRecord().get("UPSO_ADDR");   //���� �ּ�
        String   UPSO_BRA = dobj.getRetObject("R").getRecord().get("UPSO_BRA");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_STOMU_CONTRINFO (BSCON_CD, BSCON_UPSO_CD, BSCON_UPSO_NM, UPSO_ADDR, BSCON_BSTYP_NM, UPSO_BRA, REMAK, INSPRES_ID, INS_DATE) SELECT :BSCON_CD , :BSCON_UPSO_CD , :BSCON_UPSO_NM , :UPSO_ADDR , :BSTYP_CD , :UPSO_BRA , :REMAK , :INSPRES_ID , SYSDATE FROM DUAL";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("BSCON_UPSO_NM", BSCON_UPSO_NM);               //Ÿ��ü���Ҹ�
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //���� �ּ�
        sobj.setString("UPSO_BRA", UPSO_BRA);               //������
        return sobj;
    }
    // ���� ������ ����
    public DOBJ CALLstomu_save_INS18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS18");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_INS18(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_STOMU_ERR (UPSO_CD)  \n";
        query +=" values(:UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // Ÿ��ü�������
    // Ÿ��ü�� ��������� ���� ����ֱ� ����
    public DOBJ CALLstomu_save_SEL32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL32");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL32");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstomu_save_SEL32(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL32");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_SEL32(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �����ڵ� Ȯ��
    // �ٲ� ���ҿ� �ٲ�� �� ���ҿ� ���� ��Ҹ� �������ֱ� ����
    public DOBJ CALLstomu_save_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstomu_save_SEL14(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_STOMU_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    // ���� ������ ����
    public DOBJ CALLstomu_save_INS34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS34");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS34");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_INS34(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_INS34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_STOMU_ERR (UPSO_CD)  \n";
        query +=" values(:UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� ���Ҹ� Ȯ��
    // ���Ҹ� ����� �̷��߰�
    public DOBJ CALLstomu_save_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstomu_save_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSCON_UPSO_NM  FROM  GIBU.TBRA_STOMU_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    // �����Է�
    public DOBJ CALLstomu_save_XIUD40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD40");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD40");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_XIUD40(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD40");
        rvobj.Println("XIUD40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_XIUD40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   BSCON_UPSO_NM = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_NM");   //Ÿ��ü���Ҹ�
        String   BSTYP_CD = dobj.getRetObject("R").getRecord().get("BSTYP_NM");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   UPSO_ADDR = dobj.getRetObject("R").getRecord().get("UPSO_ADDR");   //���� �ּ�
        String   UPSO_BRA = dobj.getRetObject("R").getRecord().get("UPSO_BRA");   //������
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_STOMU_CONTRINFO (BSCON_CD, BSCON_UPSO_CD, BSCON_UPSO_NM, UPSO_CD, UPSO_ADDR, MONPRNCFEE, BSCON_BSTYP_NM, UPSO_BRA, REMAK, INSPRES_ID, INS_DATE) SELECT :BSCON_CD , :BSCON_UPSO_CD , :BSCON_UPSO_NM , :UPSO_CD , :UPSO_ADDR , (SELECT NVL(MONPRNCFEE,0) FROM (SELECT MONPRNCFEE FROM GIBU.TBRA_UPSORTAL_INFO WHERE UPSO_CD = :UPSO_CD ORDER BY APPL_DAY DESC) WHERE ROWNUM = 1) , :BSTYP_CD , :UPSO_BRA , :REMAK , :INSPRES_ID , SYSDATE FROM DUAL";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("BSCON_UPSO_NM", BSCON_UPSO_NM);               //Ÿ��ü���Ҹ�
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //���� �ּ�
        sobj.setString("UPSO_BRA", UPSO_BRA);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����̷� �Է�
    // �����ڵ庯�������̷�
    public DOBJ CALLstomu_save_XIUD36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD36");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD36");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_XIUD36(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD36");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_XIUD36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_STOMU_HISTY (BSCON_CD, BSCON_UPSO_CD, SEQ, CNG_COL, BEFO_CNG, AFT_CNG, INSPRES_ID, INS_DATE, REMAK) SELECT :BSCON_CD AS BSCON_CD , :BSCON_UPSO_CD AS BSCON_UPSO_CD , (SELECT NVL(MAX(SEQ),0)+1 FROM GIBU.TBRA_STOMU_HISTY WHERE BSCON_CD = :BSCON_CD AND BSCON_UPSO_CD = :BSCON_UPSO_CD) AS SEQ , '�����ڵ�' AS CNG_COL , '' AS BEFO_CNG , :UPSO_CD AS AFT_CNG , :INSPRES_ID AS INSPRES_ID , SYSDATE AS INS_DATE , :REMAK FROM DUAL";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �����ּ� Ȯ��
    // �����ּ� ����� �̷� �߰�
    public DOBJ CALLstomu_save_SEL21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL21");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL21");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstomu_save_SEL21(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_SEL21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_ADDR  FROM  GIBU.TBRA_STOMU_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    // ������� ���ι������
    public DOBJ CALLstomu_save_UPD38(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD38");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD38");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_UPD38(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD38") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_UPD38(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PAPER_CANC_YN ="Y";   //���� ��� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //�����ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set PAPER_CANC_YN=:PAPER_CANC_YN  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //�����ڵ�
        return sobj;
    }
    // �����̷� �Է�
    // �����ּ������̷�
    public DOBJ CALLstomu_save_XIUD22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD22");
        VOBJ dvobj = dobj.getRetObject("SEL14");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("XIUD22");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_XIUD22(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD22");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_XIUD22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   UPSO_ADDR = dobj.getRetObject("R").getRecord().get("UPSO_ADDR");   //���� �ּ�
        String   UPSO_ADDR_BEFO = dobj.getRetObject("SEL21").getRecord().get("UPSO_ADDR");   //UPSO_ADDR_BEFO
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_STOMU_HISTY (BSCON_CD, BSCON_UPSO_CD, SEQ, CNG_COL, BEFO_CNG, AFT_CNG, INSPRES_ID, INS_DATE, REMAK) SELECT :BSCON_CD AS BSCON_CD , :BSCON_UPSO_CD AS BSCON_UPSO_CD , (SELECT NVL(MAX(SEQ),0)+1 FROM GIBU.TBRA_STOMU_HISTY WHERE BSCON_CD = :BSCON_CD AND BSCON_UPSO_CD = :BSCON_UPSO_CD) AS SEQ , '�����ּ�' AS CNG_COL , :UPSO_ADDR_BEFO AS BEFO_CNG , :UPSO_ADDR AS AFT_CNG , :INSPRES_ID AS INSPRES_ID , SYSDATE AS INS_DATE , :REMAK FROM DUAL";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //���� �ּ�
        sobj.setString("UPSO_ADDR_BEFO", UPSO_ADDR_BEFO);               //UPSO_ADDR_BEFO
        return sobj;
    }
    // ���� ������ Ȯ��
    // ������ ����� �̷� �߰�
    public DOBJ CALLstomu_save_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstomu_save_SEL24(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_BRA  FROM  GIBU.TBRA_STOMU_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    // �����̷� �Է�
    // �������������̷�
    public DOBJ CALLstomu_save_XIUD25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD25");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD25");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_XIUD25(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_XIUD25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   UPSO_BRA = dobj.getRetObject("R").getRecord().get("UPSO_BRA");   //������
        String   UPSO_BRA_BEFO = dobj.getRetObject("SEL24").getRecord().get("UPSO_BRA");   //UPSO_BRA_BEFO
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_STOMU_HISTY (BSCON_CD, BSCON_UPSO_CD, SEQ, CNG_COL, BEFO_CNG, AFT_CNG, INSPRES_ID, INS_DATE, REMAK) SELECT :BSCON_CD AS BSCON_CD , :BSCON_UPSO_CD AS BSCON_UPSO_CD , (SELECT NVL(MAX(SEQ),0)+1 FROM GIBU.TBRA_STOMU_HISTY WHERE BSCON_CD = :BSCON_CD AND BSCON_UPSO_CD = :BSCON_UPSO_CD) AS SEQ , '������' AS CNG_COL , :UPSO_BRA_BEFO AS BEFO_CNG , :UPSO_BRA AS AFT_CNG , :INSPRES_ID AS INSPRES_ID , SYSDATE AS INS_DATE , :REMAK FROM DUAL";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_BRA", UPSO_BRA);               //������
        sobj.setString("UPSO_BRA_BEFO", UPSO_BRA_BEFO);               //UPSO_BRA_BEFO
        return sobj;
    }
    // �������ǻ���� ������Ʈ
    public DOBJ CALLstomu_save_XIUD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD11");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD11");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_XIUD11(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_XIUD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   BSCON_UPSO_NM = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_NM");   //Ÿ��ü���Ҹ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   UPSO_ADDR = dobj.getRetObject("R").getRecord().get("UPSO_ADDR");   //���� �ּ�
        String   UPSO_BRA = dobj.getRetObject("R").getRecord().get("UPSO_BRA");   //������
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_STOMU_CONTRINFO  \n";
        query +=" SET UPSO_CD = :UPSO_CD , MONPRNCFEE = (SELECT NVL(MONPRNCFEE,0) FROM (SELECT MONPRNCFEE FROM  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" ORDER BY APPL_DAY DESC)  \n";
        query +=" WHERE ROWNUM = 1) , BSCON_UPSO_NM = :BSCON_UPSO_NM , UPSO_ADDR = :UPSO_ADDR , UPSO_BRA = :UPSO_BRA , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , REMAK = :REMAK  \n";
        query +=" WHERE BSCON_CD = :BSCON_CD  \n";
        query +=" AND BSCON_UPSO_CD = :BSCON_UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("BSCON_UPSO_NM", BSCON_UPSO_NM);               //Ÿ��ü���Ҹ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //���� �ּ�
        sobj.setString("UPSO_BRA", UPSO_BRA);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����̷� �Է�
    // ���Ҹ��������̷�
    public DOBJ CALLstomu_save_XIUD20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD20");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD20");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_XIUD20(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_XIUD20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   BSCON_UPSO_NM = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_NM");   //Ÿ��ü���Ҹ�
        String   BSCON_UPSO_NM_BEFO = dobj.getRetObject("SEL19").getRecord().get("BSCON_UPSO_NM");   //�������Ҹ�(����)
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_STOMU_HISTY (BSCON_CD, BSCON_UPSO_CD, SEQ, CNG_COL, BEFO_CNG, AFT_CNG, INSPRES_ID, INS_DATE, REMAK) SELECT :BSCON_CD AS BSCON_CD , :BSCON_UPSO_CD AS BSCON_UPSO_CD , (SELECT NVL(MAX(SEQ),0)+1 FROM GIBU.TBRA_STOMU_HISTY WHERE BSCON_CD = :BSCON_CD AND BSCON_UPSO_CD = :BSCON_UPSO_CD) AS SEQ , '���Ҹ�(����)' AS CNG_COL , :BSCON_UPSO_NM_BEFO AS BEFO_CNG , :BSCON_UPSO_NM AS AFT_CNG , :INSPRES_ID AS INSPRES_ID , SYSDATE AS INS_DATE , :REMAK FROM DUAL";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("BSCON_UPSO_NM", BSCON_UPSO_NM);               //Ÿ��ü���Ҹ�
        sobj.setString("BSCON_UPSO_NM_BEFO", BSCON_UPSO_NM_BEFO);               //�������Ҹ�(����)
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REMAK", REMAK);               //���
        return sobj;
    }
    // �����̷� �Է�
    // �����ڵ庯�������̷�
    public DOBJ CALLstomu_save_XIUD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD10");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD10");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_XIUD10(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_XIUD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   UPSO_CD_BEFO = dobj.getRetObject("SEL14").getRecord().get("UPSO_CD");   //UPSO_CD_BEFO
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_STOMU_HISTY (BSCON_CD, BSCON_UPSO_CD, SEQ, CNG_COL, BEFO_CNG, AFT_CNG, INSPRES_ID, INS_DATE, REMAK) SELECT :BSCON_CD AS BSCON_CD , :BSCON_UPSO_CD AS BSCON_UPSO_CD , (SELECT NVL(MAX(SEQ),0)+1 FROM GIBU.TBRA_STOMU_HISTY WHERE BSCON_CD = :BSCON_CD AND BSCON_UPSO_CD = :BSCON_UPSO_CD) AS SEQ , '�����ڵ�' AS CNG_COL , :UPSO_CD_BEFO AS BEFO_CNG , :UPSO_CD AS AFT_CNG , :INSPRES_ID AS INSPRES_ID , SYSDATE AS INS_DATE , :REMAK FROM DUAL";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("UPSO_CD_BEFO", UPSO_CD_BEFO);               //UPSO_CD_BEFO
        return sobj;
    }
    // �������� ���������
    public DOBJ CALLstomu_save_UPD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD17");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD17");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_UPD17(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD17") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_UPD17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PAPER_CANC_YN ="N";   //���� ��� ����
        String   UPSO_CD = dobj.getRetObject("SEL14").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set PAPER_CANC_YN=:PAPER_CANC_YN  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������� ���ι������
    public DOBJ CALLstomu_save_UPD18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD18");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstomu_save_UPD18(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_UPD18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PAPER_CANC_YN ="Y";   //���� ��� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //�����ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set PAPER_CANC_YN=:PAPER_CANC_YN  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //�����ڵ�
        return sobj;
    }
    // Ÿ��ü�ߺ�����
    public DOBJ CALLstomu_save_SEL16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL16");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL16");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstomu_save_SEL16(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL16");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_save_SEL16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_STOMU_ERR ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$stomu_save
    //##**$$stomu_search
    /*
    */
    public DOBJ CTLstomu_search(DOBJ dobj)
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
            dobj  = CALLstomu_search_SEL1(Conn, dobj);           //  �������ǻ���� ������� ��ȸ
            dobj  = CALLstomu_search_SEL2(Conn, dobj);           //  �����̷����� ��ȸ
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
    public DOBJ CTLstomu_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstomu_search_SEL1(Conn, dobj);           //  �������ǻ���� ������� ��ȸ
        dobj  = CALLstomu_search_SEL2(Conn, dobj);           //  �����̷����� ��ȸ
        return dobj;
    }
    // �������ǻ���� ������� ��ȸ
    public DOBJ CALLstomu_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstomu_search_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MATCH_GBN = dobj.getRetObject("S").getRecord().get("MATCH_GBN");   //��Ī ����
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.BSCON_CD , (SELECT BSCONHAN_NM  ";
        query +=" FROM GIBU.TBRA_BSCON_STOMU  ";
        query +=" WHERE BSCON_CD = A.BSCON_CD) AS BSCONHAN_NM , A.BSCON_UPSO_CD , A.BSCON_UPSO_NM , A.UPSO_CD , A.UPSO_ADDR , A.MONPRNCFEE , A.BSCON_BSTYP_NM AS BSTYP_NM , DECODE(A.UPSO_CD, NULL, 'N', 'Y') AS MATCH_GBN , DECODE(B.PAPER_CANC_YN, 'Y', 1, 0) AS PAPER_CANC_YN , DECODE(A.MOD_DATE, NULL, 'N', DECODE((SELECT NVL(MAX(SEQ),1)  ";
        query +=" FROM GIBU.TBRA_STOMU_HISTY  ";
        query +=" WHERE BSCON_CD = BSCON_CD  ";
        query +=" AND BSCON_UPSO_CD = BSCON_UPSO_CD) , 1, 'N', 'Y')) AS MOD_YN , TO_CHAR(NVL(A.MOD_DATE, A.INS_DATE), 'YYYYMMDD') AS CNG_DATE , A.UPSO_BRA , A.REMAK , 'S' AS GBN  ";
        query +=" FROM GIBU.TBRA_STOMU_CONTRINFO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE 1=1  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD(+)  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND A.BSCON_CD = :BSCON_CD  ";
        }
        if( !MATCH_GBN.equals("") )
        {
            query +=" AND DECODE(A.UPSO_CD, NULL, 'N', 'Y') = :MATCH_GBN  ";
        }
        query +=" ORDER BY BSCON_UPSO_CD  ";
        sobj.setSql(query);
        if(!MATCH_GBN.equals(""))
        {
            sobj.setString("MATCH_GBN", MATCH_GBN);               //��Ī ����
        }
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        }
        return sobj;
    }
    // �����̷����� ��ȸ
    public DOBJ CALLstomu_search_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstomu_search_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_search_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("S1").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   BSCON_CD = dobj.getRetObject("S1").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BSCON_CD  ,   \n";
        query +=" (SELECT  BSCONHAN_NM  FROM  GIBU.TBRA_BSCON_STOMU  WHERE  BSCON_CD  =  A.BSCON_CD)  AS  BSCONHAN_NM  ,  A.BSCON_UPSO_CD  ,  A.SEQ  ,  A.CNG_COL  ,  DECODE(A.CNG_COL,'BSCON_UPSO_NM',  '���Ҹ�(����)',  'UPSO_CD',  '��Ī����',  'UPSO_ADDR',  '�ּ�',  'BSTYP_CD',  '����',  A.CNG_COL)  AS  CNG_COL_NM  ,  A.BEFO_CNG  ,  A.AFT_CNG  ,  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  AS  STAFF_NM  ,  A.INS_DATE  ,  A.REMAK  FROM  GIBU.TBRA_STOMU_HISTY  A  WHERE  A.BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  A.BSCON_UPSO_CD  =  :BSCON_UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    //##**$$stomu_search
    //##**$$end
}
