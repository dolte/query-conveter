
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s04_3
{
    public bra01_s04_3()
    {
    }
    //##**$$closed_mng_branch_2
    /*
    */
    public DOBJ CTLclosed_mng_branch_2(DOBJ dobj)
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
            dobj  = CALLclosed_mng_branch_2_MIUD15(Conn, dobj);           //  ����
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLclosed_mng_branch_2_SEL18(Conn, dobj);           //  �޾�������
            dobj  = CALLclosed_mng_branch_2_SEL45(Conn, dobj);           //  ���������
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
    public DOBJ CTLclosed_mng_branch_2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLclosed_mng_branch_2_MIUD15(Conn, dobj);           //  ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLclosed_mng_branch_2_SEL18(Conn, dobj);           //  �޾�������
        dobj  = CALLclosed_mng_branch_2_SEL45(Conn, dobj);           //  ���������
        return dobj;
    }
    // ����
    public DOBJ CALLclosed_mng_branch_2_MIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD15");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLclosed_mng_branch_2_SEL36(Conn, dobj);           //  �б�
                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                {
                    dobj  = CALLclosed_mng_branch_2_SEL37(Conn, dobj);           //  ����ΰ���Աݿ���Ȯ��
                    dobj  = CALLclosed_mng_branch_2_SEL38(Conn, dobj);           //  ���ҹ湮��� Ȯ��
                    if( dobj.getRetObject("SEL37").getRecord().getDouble("CNT") > 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="�ش��� Ȥ�� ���Ŀ� �Աݳ����� �����մϴ�. �ٽ� Ȯ���� �ֽñ� �ٶ��ϴ�.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else if( dobj.getRetObject("R").getRecord().get("GBN").equals("01") && dobj.getRetObject("SEL38").getRecord().getDouble("CNT") == 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="���ҹ湮����� �������� �ְ�, ÷�������� �Ѱ� �̻� �ʿ��մϴ�.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_branch_2_MRG38( dobj);        //  �������
                        dobj  = CALLclosed_mng_branch_2_SEL20(Conn, dobj);           //  CLSED_NUM����
                        dobj  = CALLclosed_mng_branch_2_SEL81(Conn, dobj);           //  SEQ����
                        dobj  = CALLclosed_mng_branch_2_INS75(Conn, dobj);           //  �����ݾ� ���
                        dobj  = CALLclosed_mng_branch_2_INS13(Conn, dobj);           //  ��������
                        if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                        {
                            dobj  = CALLclosed_mng_branch_2_SEL44(Conn, dobj);           //  �Աݿ�
                            dobj  = CALLclosed_mng_branch_2_MRG46( dobj);        //  �Աݿ������ϱ�
                            dobj  = CALLclosed_mng_branch_2_XIUD49(Conn, dobj);           //  ������Աݵ��
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_2_XIUD22(Conn, dobj);           //  ������������
                                dobj  = CALLclosed_mng_branch_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_branch_2_SEL47(Conn, dobj);           //  �Աݿ�
                            dobj  = CALLclosed_mng_branch_2_MRG46( dobj);        //  �Աݿ������ϱ�
                            dobj  = CALLclosed_mng_branch_2_XIUD49(Conn, dobj);           //  ������Աݵ��
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_2_XIUD22(Conn, dobj);           //  ������������
                                dobj  = CALLclosed_mng_branch_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                        }
                    }
                }
                else
                {
                    dobj  = CALLclosed_mng_branch_2_SEL23(Conn, dobj);           //  �Աݿ���Ȯ��
                    dobj  = CALLclosed_mng_branch_2_SEL24(Conn, dobj);           //  ���ҹ湮��� Ȯ��
                    if( dobj.getRetObject("SEL23").getRecord().getDouble("CNT") > 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="�ش����� �Աݳ����� �����մϴ�. �ٽ� Ȯ���� �ֽñ� �ٶ��ϴ�.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else if( ( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("11") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("12") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("13") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("15") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("16") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("17") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("18") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("19") ) && dobj.getRetObject("SEL24").getRecord().getDouble("CNT") == 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="���ҹ湮����� �޾������ �ְ�, ÷�������� �Ѱ� �̻� �ʿ��մϴ�.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_branch_2_MRG38( dobj);        //  �������
                        dobj  = CALLclosed_mng_branch_2_SEL20(Conn, dobj);           //  CLSED_NUM����
                        dobj  = CALLclosed_mng_branch_2_SEL81(Conn, dobj);           //  SEQ����
                        dobj  = CALLclosed_mng_branch_2_INS75(Conn, dobj);           //  �����ݾ� ���
                        dobj  = CALLclosed_mng_branch_2_INS13(Conn, dobj);           //  ��������
                        if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                        {
                            dobj  = CALLclosed_mng_branch_2_SEL44(Conn, dobj);           //  �Աݿ�
                            dobj  = CALLclosed_mng_branch_2_MRG46( dobj);        //  �Աݿ������ϱ�
                            dobj  = CALLclosed_mng_branch_2_XIUD49(Conn, dobj);           //  ������Աݵ��
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_2_XIUD22(Conn, dobj);           //  ������������
                                dobj  = CALLclosed_mng_branch_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_branch_2_SEL47(Conn, dobj);           //  �Աݿ�
                            dobj  = CALLclosed_mng_branch_2_MRG46( dobj);        //  �Աݿ������ϱ�
                            dobj  = CALLclosed_mng_branch_2_XIUD49(Conn, dobj);           //  ������Աݵ��
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_2_XIUD22(Conn, dobj);           //  ������������
                                dobj  = CALLclosed_mng_branch_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                        }
                    }
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLclosed_mng_branch_2_SEL51(Conn, dobj);           //  ����üũ
                if( dobj.getRetObject("SEL51").getRecord().getDouble("CNT") > 0)
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="������ ���Դϴ�. �����Ҽ� �����ϴ�.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
                }
                else
                {
                    dobj  = CALLclosed_mng_branch_2_DEL48(Conn, dobj);           //  ������������
                    if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                    {
                        dobj  = CALLclosed_mng_branch_2_SEL55(Conn, dobj);           //  �Աݿ���Ȯ��
                        dobj  = CALLclosed_mng_branch_2_SEL56(Conn, dobj);           //  ���ҹ湮��� Ȯ��
                        if( dobj.getRetObject("SEL55").getRecord().getDouble("CNT") > 0)
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="�ش��� Ȥ�� ���Ŀ� �Աݳ����� �����մϴ�. �ٽ� Ȯ���� �ֽñ� �ٶ��ϴ�.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                        else if( dobj.getRetObject("R").getRecord().get("GBN").equals("01") && dobj.getRetObject("SEL56").getRecord().getDouble("CNT") == 0)
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="���ҹ湮����� �������� �ְ�, ÷�������� �Ѱ� �̻� �ʿ��մϴ�.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_branch_2_MRG56( dobj);        //  �������
                            dobj  = CALLclosed_mng_branch_2_SEL83(Conn, dobj);           //  SEQ����
                            dobj  = CALLclosed_mng_branch_2_INS83(Conn, dobj);           //  �����ݾ� ���
                            dobj  = CALLclosed_mng_branch_2_UPD34(Conn, dobj);           //  �޾���������
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_2_SEL60(Conn, dobj);           //  �Աݿ�
                                dobj  = CALLclosed_mng_branch_2_MRG61( dobj);        //  �������
                                dobj  = CALLclosed_mng_branch_2_XIUD50(Conn, dobj);           //  �޾�(�Ա�)���
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_branch_2_XIUD64(Conn, dobj);           //  ������������
                                    dobj  = CALLclosed_mng_branch_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_branch_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_2_SEL39(Conn, dobj);           //  �Աݿ�
                                dobj  = CALLclosed_mng_branch_2_MRG61( dobj);        //  �������
                                dobj  = CALLclosed_mng_branch_2_XIUD50(Conn, dobj);           //  �޾�(�Ա�)���
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_branch_2_XIUD64(Conn, dobj);           //  ������������
                                    dobj  = CALLclosed_mng_branch_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_branch_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                            }
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_branch_2_SEL46(Conn, dobj);           //  �Աݿ���Ȯ��
                        dobj  = CALLclosed_mng_branch_2_SEL48(Conn, dobj);           //  ���ҹ湮��� Ȯ��
                        if( dobj.getRetObject("SEL46").getRecord().getDouble("CNT") > 0)
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="�ش����� �Աݳ����� �����մϴ�. �ٽ� Ȯ���� �ֽñ� �ٶ��ϴ�.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                        else if( ( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("11") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("12") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("13") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("15") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("16") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("17") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("18") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("19") ) && dobj.getRetObject("SEL48").getRecord().getDouble("CNT") == 0)
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="���ҹ湮����� �޾������ �ְ�, ÷�������� �Ѱ� �̻� �ʿ��մϴ�.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_branch_2_MRG56( dobj);        //  �������
                            dobj  = CALLclosed_mng_branch_2_SEL83(Conn, dobj);           //  SEQ����
                            dobj  = CALLclosed_mng_branch_2_INS83(Conn, dobj);           //  �����ݾ� ���
                            dobj  = CALLclosed_mng_branch_2_UPD34(Conn, dobj);           //  �޾���������
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_2_SEL60(Conn, dobj);           //  �Աݿ�
                                dobj  = CALLclosed_mng_branch_2_MRG61( dobj);        //  �������
                                dobj  = CALLclosed_mng_branch_2_XIUD50(Conn, dobj);           //  �޾�(�Ա�)���
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_branch_2_XIUD64(Conn, dobj);           //  ������������
                                    dobj  = CALLclosed_mng_branch_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_branch_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_2_SEL39(Conn, dobj);           //  �Աݿ�
                                dobj  = CALLclosed_mng_branch_2_MRG61( dobj);        //  �������
                                dobj  = CALLclosed_mng_branch_2_XIUD50(Conn, dobj);           //  �޾�(�Ա�)���
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_branch_2_XIUD64(Conn, dobj);           //  ������������
                                    dobj  = CALLclosed_mng_branch_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_branch_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                            }
                        }
                    }
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLclosed_mng_branch_2_SEL49(Conn, dobj);           //  ����üũ
                if( dobj.getRetObject("SEL49").getRecord().getDouble("CNT") > 0)
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="������ ���Դϴ�. �����Ҽ� �����ϴ�.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
                }
                else
                {
                    dobj  = CALLclosed_mng_branch_2_SEL54(Conn, dobj);           //  ���ıݾ�����üũ
                    if( dobj.getRetObject("SEL54").getRecord().getDouble("CNT") > 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="�����ϰ��� �ϴ� ���� ������ �Աݳ����� �����մϴ�.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_branch_2_SEL84(Conn, dobj);           //  SEQ����
                        dobj  = CALLclosed_mng_branch_2_INS85(Conn, dobj);           //  �����ݾ� ���
                        dobj  = CALLclosed_mng_branch_2_DEL49(Conn, dobj);           //  �������������
                        dobj  = CALLclosed_mng_branch_2_XIUD63(Conn, dobj);           //  �������
                        if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                        {
                            dobj  = CALLclosed_mng_branch_2_XIUD55(Conn, dobj);           //  ������������
                            dobj  = CALLclosed_mng_branch_2_OSP65(Conn, dobj);           //  ä�Ǿ���üũ
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_branch_2_OSP65(Conn, dobj);           //  ä�Ǿ���üũ
                        }
                    }
                }
            }
        }
        return dobj;
    }
    // ����üũ
    // �޾�����(CLSED_DAY)�� �ش��ϴ� ���������� �ִ��� Ȯ���Ѵ�
    public DOBJ CALLclosed_mng_branch_2_SEL49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL49");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL49");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL49(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL49");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CLSED_DAY ="";        //�޾� ����
        String   CLSED_DAY_1 = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�޾� ����
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("CLSED_BRAN");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  ,  ''  AS  DISTR_SEQ  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY_1", CLSED_DAY_1);               //�޾� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ����üũ
    // �޾�����(CLSED_DAY)�� �ش��ϴ� ���������� �ִ��� Ȯ���Ѵ�
    public DOBJ CALLclosed_mng_branch_2_SEL51(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL51");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL51");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL51(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL51");
        rvobj.Println("SEL51");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL51(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CLSED_DAY ="";        //�޾� ����
        String   CLSED_DAY_1 = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�޾� ����
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("CLSED_BRAN");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY_1", CLSED_DAY_1);               //�޾� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �б�
    public DOBJ CALLclosed_mng_branch_2_SEL36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL36");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL36");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL36(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL36");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  GBN  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // ����ΰ���Աݿ���Ȯ��
    // ����ϰ��� �ϴ� �޿� ������ �Ա��� ��ϵǾ� �ִ��� Ȯ���Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL37(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL37");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL37");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL37(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL37");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL37(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  >=  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ıݾ�����üũ
    // �����ϰ��� �ϴ� �޾�/��� ������ �Աݳ����� �����ϴ��� üũ�Ѵ� ����� ��� �޾� ������ �����ؾ� �ϹǷ� ����� �����Ѵ�
    public DOBJ CALLclosed_mng_branch_2_SEL54(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL54");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL54");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL54(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL54");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL54(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //�Ա� ��ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  NOTE_YRMN  >  (   \n";
        query +=" SELECT  MAX(NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM  )   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_GBN  <>  '14' ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������������
    // ���� ���峻���� �����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_DEL48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL48");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_DEL48(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL48") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_DEL48(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //�Ա� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NOTE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        return sobj;
    }
    // ���ҹ湮��� Ȯ��
    // ���ҹ湮����� �������� �ְ�, ÷�������� �Ѱ� �̻� �־���Ѵ�
    public DOBJ CALLclosed_mng_branch_2_SEL38(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL38");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL38");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL38(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL38");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL38(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(B.MNG_NUM)  CNT  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_CONFIRM_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.GBN  =  '2'   \n";
        query +=" AND  A.START_YRMN  =  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Աݿ���Ȯ��
    // �����ϰ��� �ϴ� ���� �̹� �Ա������� �ִ��� Ȯ���Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL55");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL55");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL55(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL55");
        rvobj.Println("SEL55");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL55(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  >=  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // SEQ����
    // ��/��� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL84(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL84");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL84");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL84(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL84");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL84(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_MISU_ADJ  WHERE  NONPY_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮��� Ȯ��
    // ���ҹ湮����� �������� �ְ�, ÷�������� �Ѱ� �̻� �־���Ѵ�
    public DOBJ CALLclosed_mng_branch_2_SEL56(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL56");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL56");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL56(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL56");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL56(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(B.MNG_NUM)  CNT  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_CONFIRM_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.GBN  =  '2'   \n";
        query +=" AND  A.START_YRMN  =  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ݾ� ���
    // ������ ����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_INS85(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS85");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS85");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_INS85(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS85") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_INS85(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String NONPY_DAY ="";        //�̳�����
        String   BEFORE_ADJ_AMT = dvobj.getRecord().get("BEFORE_ADJ_AMT");
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   ADJ_GBN ="05";   //�޾� ����
        String   BIGO ="��������� �� �ڵ�����";   //�޾� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   SEQ = dobj.getRetObject("SEL84").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_MISU_ADJ (INS_DATE, INSPRES_ID, BIGO, ADJ_AMT, NONPY_DAY, UPSO_CD, ADJ_GBN, SEQ, BEFORE_ADJ_AMT)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BIGO , :ADJ_AMT , TO_CHAR(SYSDATE, 'YYYYMMDD'), :UPSO_CD , :ADJ_GBN , :SEQ , :BEFORE_ADJ_AMT )";
        sobj.setSql(query);
        sobj.setString("BEFORE_ADJ_AMT", BEFORE_ADJ_AMT);
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("ADJ_GBN", ADJ_GBN);               //�޾� ����
        sobj.setString("BIGO", BIGO);               //�޾� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������������
    // �޾� �Ǵ� ��� ������ �����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_DEL49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL49");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_DEL49(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL49") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_DEL49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //�޾� ����
        String   CLSED_NUM = dvobj.getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   CLSED_BRAN = dvobj.getRecord().get("CLSED_BRAN");   //�޾� ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_CLSED  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and CLSED_BRAN=:CLSED_BRAN  \n";
        query +=" and CLSED_NUM=:CLSED_NUM  \n";
        query +=" and CLSED_DAY=:CLSED_DAY";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //�޾� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������
    // �޾��Ǵ� ����� �Աݿ��� ���������� �����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_MRG38(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG38");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL23, SEL37","CNT");
        rvobj.setName("MRG38") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // �������
    public DOBJ CALLclosed_mng_branch_2_XIUD63(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD63");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_XIUD63(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD63");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_XIUD63(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLSED_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�޾� ����
        String   CLSED_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");   //�޾� ����
        String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_NOTE  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND REPT_DAY = :CLSED_DAY  \n";
        query +=" AND REPT_NUM = :CLSED_NUM  \n";
        query +=" AND REPT_GBN = :CLSED_GBN";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        sobj.setString("CLSED_GBN", CLSED_GBN);               //�޾� ����
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // CLSED_NUM����
    // ��/��� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL20");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL20");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL20(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLSED_BRAN = dobj.getRetObject("R").getRecord().get("CLSED_BRAN");   //�޾� ����
        String   CLSED_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�޾� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(CLSED_NUM),  0)  +  1,  5,  '0')  CLSED_NUM  FROM  GIBU.TBRA_UPSO_CLSED  WHERE  CLSED_BRAN  =  :CLSED_BRAN   \n";
        query +=" AND  CLSED_DAY  =  :CLSED_DAY ";
        sobj.setSql(query);
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //�޾� ����
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        return sobj;
    }
    // SEQ����
    // ��/��� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL81(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL81");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL81");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL81(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL81");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL81(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_MISU_ADJ  WHERE  NONPY_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������������
    // ���ó���� ���Ҹ� ���������ش�
    public DOBJ CALLclosed_mng_branch_2_XIUD55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD55");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_XIUD55(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD55");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_XIUD55(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET CLSBS_YRMN = '' , CLSBS_INS_DAY = '' , MOD_DATE = SYSDATE , MODPRES_ID = :MODPRES_ID  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ݾ� ���
    // ������ ����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_INS75(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS75");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS75");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_INS75(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS75") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_INS75(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String NONPY_DAY ="";        //�̳�����
        String   BEFORE_ADJ_AMT = dvobj.getRecord().get("BEFORE_ADJ_AMT");
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   ADJ_GBN ="04";   //���� ����
        String   BIGO ="��������� �� �ڵ����";   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   SEQ = dobj.getRetObject("SEL81").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_MISU_ADJ (INS_DATE, INSPRES_ID, BIGO, ADJ_AMT, NONPY_DAY, UPSO_CD, ADJ_GBN, SEQ, BEFORE_ADJ_AMT)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BIGO , :ADJ_AMT , TO_CHAR(SYSDATE, 'YYYYMMDD'), :UPSO_CD , :ADJ_GBN , :SEQ , :BEFORE_ADJ_AMT )";
        sobj.setSql(query);
        sobj.setString("BEFORE_ADJ_AMT", BEFORE_ADJ_AMT);
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("ADJ_GBN", ADJ_GBN);               //���� ����
        sobj.setString("BIGO", BIGO);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������
    public DOBJ CALLclosed_mng_branch_2_MRG56(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG56");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL55, SEL46","CNT");
        rvobj.setName("MRG56") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ä�Ǿ���üũ
    // �޾�/���������� ���� ä�ǱⰣ�� �޲����� ä�ǿϷ�� ������ �������ش�
    public DOBJ CALLclosed_mng_branch_2_OSP65(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP65");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String[]  incolumns ={"GBN","UPSO_CD","COMIS","CLSED_DAY","CLSED_NUM","CLSED_GBN","DISTR_SEQ","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");         //�޾� ��ȣ
            record.put("CLSED_NUM",CLSED_NUM);
            ////
            double   COMIS = 0;         //������
            record.put("COMIS",COMIS+"");
            ////
            String   DISTR_SEQ = dobj.getRetObject("SEL49").getRecord().get("DISTR_SEQ");         //�й� ����
            record.put("DISTR_SEQ",DISTR_SEQ);
            ////
            String   GBN ="D";         //����
            record.put("GBN",GBN);
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
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
        String   spname ="GIBU.SP_PROC_CLAIM_COMPN_CHECK";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.setName("OSP65");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ��������
    // ������ ����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_INS13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS13");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_INS13(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_INS13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String INS_DATE ="";        //��� �Ͻ�
        String START_YRMN ="";        //���۳��
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //�޾� ����
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   CLSED_BRAN = dvobj.getRecord().get("CLSED_BRAN");   //�޾� ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   STTNT_DAY = dvobj.getRecord().get("STTNT_DAY");   //�Ű� ����
        String  CLSED_GBN="";          //�޾� ����
        if( ( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14") ))
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("GBN");
        }
        else
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");
        }
        String   CLSED_NUM = dobj.getRetObject("SEL20").getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_CLSED (STTNT_DAY, INS_DATE, INSPRES_ID, ADJ_AMT, UPSO_CD, CLSED_BRAN, END_YRMN, CLSED_NUM, REMAK, CLSED_GBN, START_YRMN, CLSED_DAY)  \n";
        query +=" values(:STTNT_DAY , SYSDATE, :INSPRES_ID , :ADJ_AMT , :UPSO_CD , :CLSED_BRAN , SUBSTR(:END_YRMN_1, 1, 6), :CLSED_NUM , :REMAK , :CLSED_GBN , SUBSTR(:START_YRMN_1, 1, 6), :CLSED_DAY )";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //�޾� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("CLSED_GBN", CLSED_GBN);               //�޾� ����
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // SEQ����
    // ��/��� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL83(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL83");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL83");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL83(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL83");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL83(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_MISU_ADJ  WHERE  NONPY_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ݾ� ���
    // ������ ����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_INS83(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS83");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS83");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_INS83(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS83") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_INS83(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String NONPY_DAY ="";        //�̳�����
        String   BEFORE_ADJ_AMT = dvobj.getRecord().get("BEFORE_ADJ_AMT");
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   ADJ_GBN ="05";   //�޾� ����
        String   BIGO ="��������� �� �ڵ�����";   //�޾� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   SEQ = dobj.getRetObject("SEL83").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_MISU_ADJ (INS_DATE, INSPRES_ID, BIGO, ADJ_AMT, NONPY_DAY, UPSO_CD, ADJ_GBN, SEQ, BEFORE_ADJ_AMT)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BIGO , :ADJ_AMT , TO_CHAR(SYSDATE, 'YYYYMMDD'), :UPSO_CD , :ADJ_GBN , :SEQ , :BEFORE_ADJ_AMT )";
        sobj.setSql(query);
        sobj.setString("BEFORE_ADJ_AMT", BEFORE_ADJ_AMT);
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("ADJ_GBN", ADJ_GBN);               //�޾� ����
        sobj.setString("BIGO", BIGO);               //�޾� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Աݿ�
    // ����� ���忡 ����� ���� ��ȸ�Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL44");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL44");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL44(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL44");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  ,  ''  AS  DISTR_SEQ  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  =  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        return sobj;
    }
    // �޾���������
    // ���� ����������� �����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_UPD34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD34");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_UPD34(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_UPD34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String MOD_DATE ="";        //��� �Ͻ�
        String START_YRMN ="";        //���۳��
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //�޾� ����
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   CLSED_NUM = dvobj.getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   CLSED_BRAN = dvobj.getRecord().get("CLSED_BRAN");   //�޾� ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   STTNT_DAY = dvobj.getRecord().get("STTNT_DAY");   //�Ű� ����
        String  CLSED_GBN="";          //�޾� ����
        if( ( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14") ))
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("GBN");
        }
        else
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");
        }
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_CLSED  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , STTNT_DAY=:STTNT_DAY , ADJ_AMT=:ADJ_AMT , MOD_DATE=SYSDATE , END_YRMN=SUBSTR(:END_YRMN_1, 1, 6) , REMAK=:REMAK , CLSED_GBN=:CLSED_GBN , START_YRMN=SUBSTR(:START_YRMN_1, 1, 6)  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and CLSED_BRAN=:CLSED_BRAN  \n";
        query +=" and CLSED_NUM=:CLSED_NUM  \n";
        query +=" and CLSED_DAY=:CLSED_DAY";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //�޾� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("CLSED_GBN", CLSED_GBN);               //�޾� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // �Աݿ������ϱ�
    // �޾� �Ǵ� ����� ���忡 ����� �Աݿ� ������ ���������� Ȯ���Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_MRG46(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG46");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL47, SEL44","YRMN, DISTR_SEQ");
        rvobj.setName("MRG46") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ������Աݵ��
    // �������̺�(TBRA_NOTE) �� ������ ����Ѵ�. �޾��ΰ��: FROM~TO �Ⱓ���� �޾����� �Ա�ó���� ���ش�
    public DOBJ CALLclosed_mng_branch_2_XIUD49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD49");
        VOBJ dvobj = dobj.getRetObject("MRG46");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("XIUD49");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_XIUD49(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD49");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_XIUD49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String NOTE_NUM ="";        //NOTE_NUM
        String   NOTE_NUM_2 = dobj.getRetObject("MRG46").getRecord().get("YRMN");   //���� ��ȣ
        String   NOTE_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� ��ȣ
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   CLSED_NUM = dobj.getRetObject("SEL20").getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");   //�Ա� ����
        String   REPT_YRMN = dobj.getRetObject("MRG46").getRecord().get("YRMN");   //�Ա� ���
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //�Ű� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_NOTE (UPSO_CD, NOTE_YRMN, NOTE_NUM, REPT_DAY, REPT_NUM, REPT_GBN, USE_AMT, RECV_DAY, REMAK, INSPRES_ID, INS_DATE, BRAN_CD)  \n";
        query +=" VALUES (:UPSO_CD, :REPT_YRMN, (SELECT LPAD(NVL(MAX(NOTE_NUM), 0) + 1, 4, '0') NOTE_NUM FROM GIBU.TBRA_NOTE WHERE UPSO_CD = :NOTE_NUM_1 AND NOTE_YRMN = :NOTE_NUM_2), :REPT_DAY, :CLSED_NUM, :REPT_GBN, 0, :STTNT_DAY, :REMAK, :INSPRES_ID, SYSDATE, :BRAN_CD)";
        sobj.setSql(query);
        sobj.setString("NOTE_NUM_2", NOTE_NUM_2);               //���� ��ȣ
        sobj.setString("NOTE_NUM_1", NOTE_NUM_1);               //���� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Աݿ�
    // ����� ���忡 ����� ���� ��ȸ�Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL60(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL60");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL60");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL60(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL60");
        rvobj.Println("SEL60");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL60(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  ,  ''  AS  DISTR_SEQ  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  =  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        return sobj;
    }
    // �������
    public DOBJ CALLclosed_mng_branch_2_MRG61(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG61");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL39, SEL60","YRMN, DISTR_SEQ");
        rvobj.setName("MRG61") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ������������
    // ����� ��� ��������� �������̺� ����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_XIUD22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD22");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD22");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_XIUD22(dobj, dvobj);
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
    private SQLObject SQLclosed_mng_branch_2_XIUD22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //�Ű� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET CLSBS_YRMN = SUBSTR(:START_YRMN_1, 1, 6) , CLSBS_INS_DAY = :STTNT_DAY , MOD_DATE = SYSDATE , MODPRES_ID = :MODPRES_ID  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �޾�(�Ա�)���
    // �޾��ΰ��: FROM~TO �Ⱓ���� �޾����� �Ա�ó���� ���ش�
    public DOBJ CALLclosed_mng_branch_2_XIUD50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD50");
        VOBJ dvobj = dobj.getRetObject("MRG61");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_XIUD50(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD50");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_XIUD50(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String NOTE_NUM ="";        //NOTE_NUM
        String   NOTE_NUM_2 = dobj.getRetObject("MRG61").getRecord().get("YRMN");   //���� ��ȣ
        String   NOTE_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� ��ȣ
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");   //�Ա� ����
        String   REPT_YRMN = dvobj.getRecord().get("YRMN");   //�Ա� ���
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //�Ű� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_NOTE (UPSO_CD, NOTE_YRMN, NOTE_NUM, REPT_DAY, REPT_NUM, REPT_GBN, USE_AMT, RECV_DAY, REMAK, INSPRES_ID, INS_DATE, BRAN_CD)  \n";
        query +=" VALUES (:UPSO_CD, :REPT_YRMN, (SELECT LPAD(NVL(MAX(NOTE_NUM), 0) + 1, 4, '0') NOTE_NUM FROM GIBU.TBRA_NOTE WHERE UPSO_CD = :NOTE_NUM_1 AND NOTE_YRMN = :NOTE_NUM_2), :REPT_DAY, :CLSED_NUM, :REPT_GBN, 0, :STTNT_DAY, :REMAK, :INSPRES_ID, SYSDATE, :BRAN_CD)";
        sobj.setSql(query);
        sobj.setString("NOTE_NUM_2", NOTE_NUM_2);               //���� ��ȣ
        sobj.setString("NOTE_NUM_1", NOTE_NUM_1);               //���� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ä�Ǿ���üũ
    // �޾�/���������� ���� ä�ǱⰣ�� �޲����� ä�ǿϷ�� ������ �������ش�
    public DOBJ CALLclosed_mng_branch_2_OSP63(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP63");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String[]  incolumns ={"GBN","UPSO_CD","COMIS","CLSED_DAY","CLSED_NUM","CLSED_GBN","DISTR_SEQ","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CLSED_NUM = dobj.getRetObject("SEL20").getRecord().get("CLSED_NUM");         //�޾� ��ȣ
            record.put("CLSED_NUM",CLSED_NUM);
            ////
            double   COMIS = 0;         //������
            record.put("COMIS",COMIS+"");
            ////
            String   DISTR_SEQ = dobj.getRetObject("MRG46").getRecord().get("DISTR_SEQ");         //�й� ����
            record.put("DISTR_SEQ",DISTR_SEQ);
            ////
            String   GBN ="I";         //����
            record.put("GBN",GBN);
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
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
        String   spname ="GIBU.SP_PROC_CLAIM_COMPN_CHECK";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.setName("OSP63");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �Աݿ�
    // �޾��� ���忡 ����� ���� ��ȸ�Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL47(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL47");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL47");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL47(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL47");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL47(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  ,  ''  AS  DISTR_SEQ  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  BETWEEN  SUBSTR(:START_YRMN_1,  1,  6)   \n";
        query +=" AND  SUBSTR(:END_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        return sobj;
    }
    // ������������
    // ����� ��� ��������� �������̺� ����Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_XIUD64(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD64");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_2_XIUD64(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD64");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_XIUD64(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //�Ű� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET CLSBS_YRMN = SUBSTR(:START_YRMN_1, 1, 6) , CLSBS_INS_DAY = :STTNT_DAY , MOD_DATE = SYSDATE , MODPRES_ID = :MODPRES_ID  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Աݿ���Ȯ��
    // �޾��ϰ��� �ϴ� ���� ������ �Ա��� �Ǿ� �ִ��� Ȯ���Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL23");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL23");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL23(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL23");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  BETWEEN  SUBSTR(:START_YRMN_1,  1,  6)   \n";
        query +=" AND  SUBSTR(:END_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ä�Ǿ���üũ
    // �޾�/���������� ���� ä�ǱⰣ�� �޲����� ä�ǿϷ�� ������ �������ش�
    public DOBJ CALLclosed_mng_branch_2_OSP64(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP64");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String[]  incolumns ={"GBN","UPSO_CD","COMIS","CLSED_DAY","CLSED_NUM","CLSED_GBN","DISTR_SEQ","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");         //�޾� ��ȣ
            record.put("CLSED_NUM",CLSED_NUM);
            ////
            double   COMIS = 0;         //������
            record.put("COMIS",COMIS+"");
            ////
            String   DISTR_SEQ = dobj.getRetObject("MRG61").getRecord().get("DISTR_SEQ");         //�й� ����
            record.put("DISTR_SEQ",DISTR_SEQ);
            ////
            String   GBN ="U";         //����
            record.put("GBN",GBN);
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
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
        String   spname ="GIBU.SP_PROC_CLAIM_COMPN_CHECK";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.setName("OSP64");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���ҹ湮��� Ȯ��
    // ���ҹ湮����� �޾������ �ְ�, ÷�������� �Ѱ� �̻� �־���Ѵ�
    public DOBJ CALLclosed_mng_branch_2_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL24(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(B.MNG_NUM)  CNT  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_CONFIRM_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.GBN  =  '1'   \n";
        query +=" AND  A.START_YRMN  =  SUBSTR(:START_YRMN_1,  1,  6)   \n";
        query +=" AND  A.END_YRMN  =  SUBSTR(:END_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Աݿ�
    // �޾��ϰ��� �ϴ� ���� ������ �����´�.
    public DOBJ CALLclosed_mng_branch_2_SEL39(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL39");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL39");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL39(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL39");
        rvobj.Println("SEL39");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL39(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  ,  ''  AS  DISTR_SEQ  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  BETWEEN  SUBSTR(:START_YRMN_1,  1,  6)   \n";
        query +=" AND  SUBSTR(:END_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        return sobj;
    }
    // �Աݿ���Ȯ��
    // �����ϰ��� �ϴ� ���� �̹� �Ա������� �ִ��� Ȯ���Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL46(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL46");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL46");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL46(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL46");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL46(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  BETWEEN  SUBSTR(:START_YRMN_1,  1,  6)   \n";
        query +=" AND  SUBSTR(:END_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮��� Ȯ��
    // ���ҹ湮����� �޾������ �ְ�, ÷�������� �Ѱ� �̻� �־���Ѵ�
    public DOBJ CALLclosed_mng_branch_2_SEL48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL48");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL48");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL48(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL48");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL48(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(B.MNG_NUM)  CNT  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_CONFIRM_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.GBN  =  '1'   \n";
        query +=" AND  A.START_YRMN  =  SUBSTR(:START_YRMN_1,  1,  6)   \n";
        query +=" AND  A.END_YRMN  =  SUBSTR(:END_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �޾�������
    // �޾������� ��ȸ�Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL18");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL18");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL18(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL18");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.CLSED_DAY  ,  A.CLSED_NUM  ,  A.CLSED_BRAN  ,  A.CLSED_GBN  ,  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  A.STTNT_DAY  ,  A.REMAK  ,  B.BRAN_CD  ,   \n";
        query +=" (SELECT  DECODE(SUM(NONPY_AMT),  null,  -1,  0,  0,  1)  FROM  GIBU.TBRA_MISU_CHEKWON  WHERE  BRAN_CD  =  B.BRAN_CD   \n";
        query +=" AND  NONPY_DAY  =  TO_CHAR(A.INS_DATE,  'YYYYMMDD'))  AS  MISU_CLOSED  ,  A.ADJ_AMT  ,  A.ADJ_GBN  FROM  GIBU.TBRA_UPSO_CLSED  A,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  CLSED_GBN  NOT  IN  ('14','01','02','03','04') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���������
    // ��������� ��ȸ�Ѵ�.
    public DOBJ CALLclosed_mng_branch_2_SEL45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL45");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL45");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_branch_2_SEL45(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL45");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_2_SEL45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.CLSED_DAY  ,  A.CLSED_NUM  ,  A.CLSED_BRAN  ,  '14'  AS  CLSED_GBN  ,  A.CLSED_GBN  AS  GBN  ,  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  A.STTNT_DAY  ,  A.REMAK  ,  B.BRAN_CD  ,  A.ADJ_AMT  ,  A.ADJ_GBN  FROM  GIBU.TBRA_UPSO_CLSED  A,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  CLSED_GBN  IN  ('14','01','02','03','04') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$closed_mng_branch_2
    //##**$$closed_mng_head_2
    /*
    */
    public DOBJ CTLclosed_mng_head_2(DOBJ dobj)
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
            dobj  = CALLclosed_mng_head_2_MIUD15(Conn, dobj);           //  ����
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLclosed_mng_head_2_SEL18(Conn, dobj);           //  �޾�������
            dobj  = CALLclosed_mng_head_2_SEL45(Conn, dobj);           //  ���������
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
    public DOBJ CTLclosed_mng_head_2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLclosed_mng_head_2_MIUD15(Conn, dobj);           //  ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLclosed_mng_head_2_SEL18(Conn, dobj);           //  �޾�������
        dobj  = CALLclosed_mng_head_2_SEL45(Conn, dobj);           //  ���������
        return dobj;
    }
    // ����
    public DOBJ CALLclosed_mng_head_2_MIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD15");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLclosed_mng_head_2_SEL36(Conn, dobj);           //  �б�
                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                {
                    dobj  = CALLclosed_mng_head_2_SEL37(Conn, dobj);           //  ����ΰ���Աݿ���Ȯ��
                    if( dobj.getRetObject("SEL37").getRecord().getDouble("CNT") > 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="�ش��� Ȥ�� ���Ŀ� �Աݳ����� �����մϴ�. �ٽ� Ȯ���� �ֽñ� �ٶ��ϴ�.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_head_2_MRG38( dobj);        //  �������
                        dobj  = CALLclosed_mng_head_2_SEL20(Conn, dobj);           //  CLSED_NUM����
                        dobj  = CALLclosed_mng_head_2_SEL66(Conn, dobj);           //  SEQ����
                        dobj  = CALLclosed_mng_head_2_INS67(Conn, dobj);           //  �����ݾ� ���
                        dobj  = CALLclosed_mng_head_2_INS13(Conn, dobj);           //  ��������
                        if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                        {
                            dobj  = CALLclosed_mng_head_2_SEL44(Conn, dobj);           //  �Աݿ�
                            dobj  = CALLclosed_mng_head_2_MRG46( dobj);        //  �Աݿ������ϱ�
                            dobj  = CALLclosed_mng_head_2_XIUD49(Conn, dobj);           //  ������Աݵ��
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_head_2_XIUD22(Conn, dobj);           //  ������������
                                dobj  = CALLclosed_mng_head_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_head_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_head_2_SEL47(Conn, dobj);           //  �Աݿ�
                            dobj  = CALLclosed_mng_head_2_MRG46( dobj);        //  �Աݿ������ϱ�
                            dobj  = CALLclosed_mng_head_2_XIUD49(Conn, dobj);           //  ������Աݵ��
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_head_2_XIUD22(Conn, dobj);           //  ������������
                                dobj  = CALLclosed_mng_head_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_head_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                        }
                    }
                }
                else
                {
                    dobj  = CALLclosed_mng_head_2_SEL23(Conn, dobj);           //  �Աݿ���Ȯ��
                    if( dobj.getRetObject("SEL23").getRecord().getDouble("CNT") > 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="�ش����� �Աݳ����� �����մϴ�. �ٽ� Ȯ���� �ֽñ� �ٶ��ϴ�.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_head_2_MRG38( dobj);        //  �������
                        dobj  = CALLclosed_mng_head_2_SEL20(Conn, dobj);           //  CLSED_NUM����
                        dobj  = CALLclosed_mng_head_2_SEL66(Conn, dobj);           //  SEQ����
                        dobj  = CALLclosed_mng_head_2_INS67(Conn, dobj);           //  �����ݾ� ���
                        dobj  = CALLclosed_mng_head_2_INS13(Conn, dobj);           //  ��������
                        if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                        {
                            dobj  = CALLclosed_mng_head_2_SEL44(Conn, dobj);           //  �Աݿ�
                            dobj  = CALLclosed_mng_head_2_MRG46( dobj);        //  �Աݿ������ϱ�
                            dobj  = CALLclosed_mng_head_2_XIUD49(Conn, dobj);           //  ������Աݵ��
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_head_2_XIUD22(Conn, dobj);           //  ������������
                                dobj  = CALLclosed_mng_head_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_head_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_head_2_SEL47(Conn, dobj);           //  �Աݿ�
                            dobj  = CALLclosed_mng_head_2_MRG46( dobj);        //  �Աݿ������ϱ�
                            dobj  = CALLclosed_mng_head_2_XIUD49(Conn, dobj);           //  ������Աݵ��
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_head_2_XIUD22(Conn, dobj);           //  ������������
                                dobj  = CALLclosed_mng_head_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_head_2_OSP63(Conn, dobj);           //  ä�Ǿ���üũ
                            }
                        }
                    }
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLclosed_mng_head_2_SEL51(Conn, dobj);           //  ����üũ
                if( dobj.getRetObject("SEL51").getRecord().getDouble("CNT") > 0)
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="������ ���Դϴ�. �����Ҽ� �����ϴ�.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
                }
                else
                {
                    dobj  = CALLclosed_mng_head_2_DEL48(Conn, dobj);           //  ������������
                    if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                    {
                        dobj  = CALLclosed_mng_head_2_SEL55(Conn, dobj);           //  �Աݿ���Ȯ��
                        if( dobj.getRetObject("SEL55").getRecord().getDouble("CNT") > 0)
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="�ش��� Ȥ�� ���Ŀ� �Աݳ����� �����մϴ�. �ٽ� Ȯ���� �ֽñ� �ٶ��ϴ�.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_head_2_MRG56( dobj);        //  �������
                            dobj  = CALLclosed_mng_head_2_SEL67(Conn, dobj);           //  SEQ����
                            dobj  = CALLclosed_mng_head_2_INS68(Conn, dobj);           //  �����ݾ� ���
                            dobj  = CALLclosed_mng_head_2_UPD34(Conn, dobj);           //  �޾���������
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_head_2_SEL60(Conn, dobj);           //  �Աݿ�
                                dobj  = CALLclosed_mng_head_2_MRG61( dobj);        //  �������
                                dobj  = CALLclosed_mng_head_2_XIUD50(Conn, dobj);           //  �޾�(�Ա�)���
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_head_2_XIUD64(Conn, dobj);           //  ������������
                                    dobj  = CALLclosed_mng_head_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_head_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_head_2_SEL39(Conn, dobj);           //  �Աݿ�
                                dobj  = CALLclosed_mng_head_2_MRG61( dobj);        //  �������
                                dobj  = CALLclosed_mng_head_2_XIUD50(Conn, dobj);           //  �޾�(�Ա�)���
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_head_2_XIUD64(Conn, dobj);           //  ������������
                                    dobj  = CALLclosed_mng_head_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_head_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                            }
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_head_2_SEL46(Conn, dobj);           //  �Աݿ���Ȯ��
                        if( dobj.getRetObject("SEL46").getRecord().getDouble("CNT") > 0)
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="�ش����� �Աݳ����� �����մϴ�. �ٽ� Ȯ���� �ֽñ� �ٶ��ϴ�.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_head_2_MRG56( dobj);        //  �������
                            dobj  = CALLclosed_mng_head_2_SEL67(Conn, dobj);           //  SEQ����
                            dobj  = CALLclosed_mng_head_2_INS68(Conn, dobj);           //  �����ݾ� ���
                            dobj  = CALLclosed_mng_head_2_UPD34(Conn, dobj);           //  �޾���������
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_head_2_SEL60(Conn, dobj);           //  �Աݿ�
                                dobj  = CALLclosed_mng_head_2_MRG61( dobj);        //  �������
                                dobj  = CALLclosed_mng_head_2_XIUD50(Conn, dobj);           //  �޾�(�Ա�)���
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_head_2_XIUD64(Conn, dobj);           //  ������������
                                    dobj  = CALLclosed_mng_head_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_head_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_head_2_SEL39(Conn, dobj);           //  �Աݿ�
                                dobj  = CALLclosed_mng_head_2_MRG61( dobj);        //  �������
                                dobj  = CALLclosed_mng_head_2_XIUD50(Conn, dobj);           //  �޾�(�Ա�)���
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_head_2_XIUD64(Conn, dobj);           //  ������������
                                    dobj  = CALLclosed_mng_head_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_head_2_OSP64(Conn, dobj);           //  ä�Ǿ���üũ
                                }
                            }
                        }
                    }
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLclosed_mng_head_2_SEL49(Conn, dobj);           //  ����üũ
                if( dobj.getRetObject("SEL49").getRecord().getDouble("CNT") > 0)
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="������ ���Դϴ�. �����Ҽ� �����ϴ�.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
                }
                else
                {
                    dobj  = CALLclosed_mng_head_2_SEL54(Conn, dobj);           //  ���ıݾ�����üũ
                    if( dobj.getRetObject("SEL54").getRecord().getDouble("CNT") > 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="�����ϰ��� �ϴ� ���� ������ �Աݳ����� �����մϴ�.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_head_2_SEL68(Conn, dobj);           //  SEQ����
                        dobj  = CALLclosed_mng_head_2_INS69(Conn, dobj);           //  �����ݾ� ���
                        dobj  = CALLclosed_mng_head_2_DEL49(Conn, dobj);           //  �������������
                        dobj  = CALLclosed_mng_head_2_XIUD63(Conn, dobj);           //  �������
                        if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                        {
                            dobj  = CALLclosed_mng_head_2_XIUD55(Conn, dobj);           //  ������������
                            dobj  = CALLclosed_mng_head_2_OSP65(Conn, dobj);           //  ä�Ǿ���üũ
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_head_2_OSP65(Conn, dobj);           //  ä�Ǿ���üũ
                        }
                    }
                }
            }
        }
        return dobj;
    }
    // ����üũ
    // �޾�����(CLSED_DAY)�� �ش��ϴ� ���������� �ִ��� Ȯ���Ѵ�
    public DOBJ CALLclosed_mng_head_2_SEL49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL49");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL49");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL49(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL49");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CLSED_DAY ="";        //�޾� ����
        String   CLSED_DAY_1 = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�޾� ����
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("CLSED_BRAN");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  ,  ''  AS  DISTR_SEQ  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY_1", CLSED_DAY_1);               //�޾� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ����üũ
    // �޾�����(CLSED_DAY)�� �ش��ϴ� ���������� �ִ��� Ȯ���Ѵ�
    public DOBJ CALLclosed_mng_head_2_SEL51(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL51");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL51");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL51(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL51");
        rvobj.Println("SEL51");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL51(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CLSED_DAY ="";        //�޾� ����
        String   CLSED_DAY_1 = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�޾� ����
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("CLSED_BRAN");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY_1", CLSED_DAY_1);               //�޾� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �б�
    public DOBJ CALLclosed_mng_head_2_SEL36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL36");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL36");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL36(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL36");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  GBN  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // ����ΰ���Աݿ���Ȯ��
    // ����ϰ��� �ϴ� �޿� ������ �Ա��� ��ϵǾ� �ִ��� Ȯ���Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL37(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL37");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL37");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL37(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL37");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL37(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  >=  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ıݾ�����üũ
    // �����ϰ��� �ϴ� �޾�/��� ������ �Աݳ����� �����ϴ��� üũ�Ѵ� ����� ��� �޾� ������ �����ؾ� �ϹǷ� ����� �����Ѵ�
    public DOBJ CALLclosed_mng_head_2_SEL54(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL54");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL54");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL54(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL54");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL54(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //�Ա� ��ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  NOTE_YRMN  >  (   \n";
        query +=" SELECT  MAX(NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM  )   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_GBN  <>  '14' ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������������
    // ���� ���峻���� �����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_DEL48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL48");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_DEL48(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL48") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_DEL48(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //�Ա� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NOTE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        return sobj;
    }
    // �Աݿ���Ȯ��
    // �����ϰ��� �ϴ� ���� �̹� �Ա������� �ִ��� Ȯ���Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL55");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL55");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL55(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL55");
        rvobj.Println("SEL55");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL55(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  >=  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������
    // �޾��Ǵ� ����� �Աݿ��� ���������� �����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_MRG38(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG38");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL23, SEL37","CNT");
        rvobj.setName("MRG38") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // SEQ����
    // ��/��� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL68(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL68");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL68");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL68(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL68");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL68(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_MISU_ADJ  WHERE  NONPY_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // CLSED_NUM����
    // ��/��� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL20");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL20");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL20(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLSED_BRAN = dobj.getRetObject("R").getRecord().get("CLSED_BRAN");   //�޾� ����
        String   CLSED_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�޾� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(CLSED_NUM),  0)  +  1,  5,  '0')  CLSED_NUM  FROM  GIBU.TBRA_UPSO_CLSED  WHERE  CLSED_BRAN  =  :CLSED_BRAN   \n";
        query +=" AND  CLSED_DAY  =  :CLSED_DAY ";
        sobj.setSql(query);
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //�޾� ����
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        return sobj;
    }
    // �������
    public DOBJ CALLclosed_mng_head_2_MRG56(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG56");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL55, SEL46","CNT");
        rvobj.setName("MRG56") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // �����ݾ� ���
    // ������ ����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_INS69(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS69");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS69");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_INS69(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS69") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_INS69(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String NONPY_DAY ="";        //�޾� ��ȣ
        String   BEFORE_ADJ_AMT = dvobj.getRecord().get("BEFORE_ADJ_AMT");
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   ADJ_GBN ="05";   //�޾� ����
        String   BIGO ="��������� �� �ڵ����";   //�޾� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   SEQ = dobj.getRetObject("SEL68").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_MISU_ADJ (INS_DATE, INSPRES_ID, BIGO, ADJ_AMT, NONPY_DAY, UPSO_CD, ADJ_GBN, SEQ, BEFORE_ADJ_AMT)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BIGO , :ADJ_AMT , TO_CHAR(SYSDATE, 'YYYYMMDD'), :UPSO_CD , :ADJ_GBN , :SEQ , :BEFORE_ADJ_AMT )";
        sobj.setSql(query);
        sobj.setString("BEFORE_ADJ_AMT", BEFORE_ADJ_AMT);
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("ADJ_GBN", ADJ_GBN);               //�޾� ����
        sobj.setString("BIGO", BIGO);               //�޾� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // SEQ����
    // ��/��� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL66(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL66");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL66");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL66(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL66");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL66(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_MISU_ADJ  WHERE  NONPY_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // SEQ����
    // ��/��� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL67(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL67");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL67");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL67(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL67");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL67(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_MISU_ADJ  WHERE  NONPY_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������������
    // �޾� �Ǵ� ��� ������ �����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_DEL49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL49");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_DEL49(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL49") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_DEL49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //�޾� ����
        String   CLSED_NUM = dvobj.getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   CLSED_BRAN = dvobj.getRecord().get("CLSED_BRAN");   //�޾� ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_CLSED  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and CLSED_BRAN=:CLSED_BRAN  \n";
        query +=" and CLSED_NUM=:CLSED_NUM  \n";
        query +=" and CLSED_DAY=:CLSED_DAY";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //�޾� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ݾ� ���
    // ������ ����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_INS67(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS67");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS67");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_INS67(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS67") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_INS67(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String NONPY_DAY ="";        //�޾� ��ȣ
        String   BEFORE_ADJ_AMT = dvobj.getRecord().get("BEFORE_ADJ_AMT");
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   ADJ_GBN ="04";   //�޾� ����
        String   BIGO ="��������� �� �ڵ����";   //�޾� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   SEQ = dobj.getRetObject("SEL66").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_MISU_ADJ (INS_DATE, INSPRES_ID, BIGO, ADJ_AMT, NONPY_DAY, UPSO_CD, ADJ_GBN, SEQ, BEFORE_ADJ_AMT)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BIGO , :ADJ_AMT , TO_CHAR(SYSDATE, 'YYYYMMDD'), :UPSO_CD , :ADJ_GBN , :SEQ , :BEFORE_ADJ_AMT )";
        sobj.setSql(query);
        sobj.setString("BEFORE_ADJ_AMT", BEFORE_ADJ_AMT);
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("ADJ_GBN", ADJ_GBN);               //�޾� ����
        sobj.setString("BIGO", BIGO);               //�޾� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ݾ� ���
    // ������ ����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_INS68(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS68");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS68");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_INS68(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS68") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_INS68(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String NONPY_DAY ="";        //�޾� ��ȣ
        String   BEFORE_ADJ_AMT = dvobj.getRecord().get("BEFORE_ADJ_AMT");
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   ADJ_GBN ="05";   //�޾� ����
        String   BIGO ="��������� �� �ڵ�����";   //�޾� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   SEQ = dobj.getRetObject("SEL67").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_MISU_ADJ (INS_DATE, INSPRES_ID, BIGO, ADJ_AMT, NONPY_DAY, UPSO_CD, ADJ_GBN, SEQ, BEFORE_ADJ_AMT)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BIGO , :ADJ_AMT , TO_CHAR(SYSDATE, 'YYYYMMDD'), :UPSO_CD , :ADJ_GBN , :SEQ , :BEFORE_ADJ_AMT )";
        sobj.setSql(query);
        sobj.setString("BEFORE_ADJ_AMT", BEFORE_ADJ_AMT);
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("ADJ_GBN", ADJ_GBN);               //�޾� ����
        sobj.setString("BIGO", BIGO);               //�޾� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������
    public DOBJ CALLclosed_mng_head_2_XIUD63(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD63");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_XIUD63(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD63");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_XIUD63(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLSED_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�޾� ����
        String   CLSED_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");   //�޾� ����
        String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_NOTE  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND REPT_DAY = :CLSED_DAY  \n";
        query +=" AND REPT_NUM = :CLSED_NUM  \n";
        query +=" AND REPT_GBN = :CLSED_GBN";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        sobj.setString("CLSED_GBN", CLSED_GBN);               //�޾� ����
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �޾���������
    // ���� ����������� �����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_UPD34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD34");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_UPD34(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_UPD34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String MOD_DATE ="";        //��� �Ͻ�
        String START_YRMN ="";        //���۳��
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //�޾� ����
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   CLSED_NUM = dvobj.getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   CLSED_BRAN = dvobj.getRecord().get("CLSED_BRAN");   //�޾� ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   STTNT_DAY = dvobj.getRecord().get("STTNT_DAY");   //�Ű� ����
        String  CLSED_GBN="";          //�޾� ����
        if( ( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14") ))
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("GBN");
        }
        else
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");
        }
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_CLSED  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , STTNT_DAY=:STTNT_DAY , ADJ_AMT=:ADJ_AMT , MOD_DATE=SYSDATE , END_YRMN=SUBSTR(:END_YRMN_1, 1, 6) , REMAK=:REMAK , CLSED_GBN=:CLSED_GBN , START_YRMN=SUBSTR(:START_YRMN_1, 1, 6)  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and CLSED_BRAN=:CLSED_BRAN  \n";
        query +=" and CLSED_NUM=:CLSED_NUM  \n";
        query +=" and CLSED_DAY=:CLSED_DAY";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //�޾� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("CLSED_GBN", CLSED_GBN);               //�޾� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // ��������
    // ������ ����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_INS13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_INS13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_INS13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String INS_DATE ="";        //��� �Ͻ�
        String START_YRMN ="";        //���۳��
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //�޾� ����
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   CLSED_BRAN = dvobj.getRecord().get("CLSED_BRAN");   //�޾� ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   STTNT_DAY = dvobj.getRecord().get("STTNT_DAY");   //�Ű� ����
        String  CLSED_GBN="";          //�޾� ����
        if( ( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14") ))
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("GBN");
        }
        else
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");
        }
        String   CLSED_NUM = dobj.getRetObject("SEL20").getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_CLSED (STTNT_DAY, INS_DATE, INSPRES_ID, ADJ_AMT, UPSO_CD, CLSED_BRAN, END_YRMN, CLSED_NUM, REMAK, CLSED_GBN, START_YRMN, CLSED_DAY)  \n";
        query +=" values(:STTNT_DAY , SYSDATE, :INSPRES_ID , :ADJ_AMT , :UPSO_CD , :CLSED_BRAN , SUBSTR(:END_YRMN_1, 1, 6), :CLSED_NUM , :REMAK , :CLSED_GBN , SUBSTR(:START_YRMN_1, 1, 6), :CLSED_DAY )";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //�޾� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("CLSED_GBN", CLSED_GBN);               //�޾� ����
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ������������
    // ���ó���� ���Ҹ� ���������ش�
    public DOBJ CALLclosed_mng_head_2_XIUD55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD55");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_XIUD55(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD55");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_XIUD55(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET CLSBS_YRMN = '' , CLSBS_INS_DAY = '' , MOD_DATE = SYSDATE , MODPRES_ID = :MODPRES_ID  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Աݿ�
    // ����� ���忡 ����� ���� ��ȸ�Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL44");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL44");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL44(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL44");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  ,  ''  AS  DISTR_SEQ  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  =  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        return sobj;
    }
    // �Աݿ�
    // ����� ���忡 ����� ���� ��ȸ�Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL60(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL60");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL60");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL60(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL60");
        rvobj.Println("SEL60");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL60(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  ,  ''  AS  DISTR_SEQ  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  =  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        return sobj;
    }
    // ä�Ǿ���üũ
    // �޾�/���������� ���� ä�ǱⰣ�� �޲����� ä�ǿϷ�� ������ �������ش�
    public DOBJ CALLclosed_mng_head_2_OSP65(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP65");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String[]  incolumns ={"GBN","UPSO_CD","COMIS","CLSED_DAY","CLSED_NUM","CLSED_GBN","DISTR_SEQ","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");         //�޾� ��ȣ
            record.put("CLSED_NUM",CLSED_NUM);
            ////
            double   COMIS = 0;         //������
            record.put("COMIS",COMIS+"");
            ////
            String   DISTR_SEQ = dobj.getRetObject("SEL49").getRecord().get("DISTR_SEQ");         //�й� ����
            record.put("DISTR_SEQ",DISTR_SEQ);
            ////
            String   GBN ="D";         //����
            record.put("GBN",GBN);
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
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
        String   spname ="GIBU.SP_PROC_CLAIM_COMPN_CHECK";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.setName("OSP65");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �Աݿ������ϱ�
    // �޾� �Ǵ� ����� ���忡 ����� �Աݿ� ������ ���������� Ȯ���Ѵ�.
    public DOBJ CALLclosed_mng_head_2_MRG46(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG46");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL47, SEL44","YRMN, DISTR_SEQ");
        rvobj.setName("MRG46") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // �������
    public DOBJ CALLclosed_mng_head_2_MRG61(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG61");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL39, SEL60","YRMN, DISTR_SEQ");
        rvobj.setName("MRG61") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ������Աݵ��
    // �������̺�(TBRA_NOTE) �� ������ ����Ѵ�. �޾��ΰ��: FROM~TO �Ⱓ���� �޾����� �Ա�ó���� ���ش�
    public DOBJ CALLclosed_mng_head_2_XIUD49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD49");
        VOBJ dvobj = dobj.getRetObject("MRG46");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_XIUD49(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD49");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_XIUD49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String NOTE_NUM ="";        //NOTE_NUM
        String   NOTE_NUM_2 = dobj.getRetObject("MRG46").getRecord().get("YRMN");   //���� ��ȣ
        String   NOTE_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� ��ȣ
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   CLSED_NUM = dobj.getRetObject("SEL20").getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");   //�Ա� ����
        String   REPT_YRMN = dvobj.getRecord().get("YRMN");   //�Ա� ���
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //�Ű� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_NOTE (UPSO_CD, NOTE_YRMN, NOTE_NUM, REPT_DAY, REPT_NUM, REPT_GBN, USE_AMT, RECV_DAY, REMAK, INSPRES_ID, INS_DATE, BRAN_CD)  \n";
        query +=" VALUES (:UPSO_CD, :REPT_YRMN, (SELECT LPAD(NVL(MAX(NOTE_NUM), 0) + 1, 4, '0') NOTE_NUM FROM GIBU.TBRA_NOTE WHERE UPSO_CD = :NOTE_NUM_1 AND NOTE_YRMN = :NOTE_NUM_2), :REPT_DAY, :CLSED_NUM, :REPT_GBN, 0, :STTNT_DAY, :REMAK, :INSPRES_ID, SYSDATE, :BRAN_CD)";
        sobj.setSql(query);
        sobj.setString("NOTE_NUM_2", NOTE_NUM_2);               //���� ��ȣ
        sobj.setString("NOTE_NUM_1", NOTE_NUM_1);               //���� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �޾�(�Ա�)���
    // �޾��ΰ��: FROM~TO �Ⱓ���� �޾����� �Ա�ó���� ���ش�
    public DOBJ CALLclosed_mng_head_2_XIUD50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD50");
        VOBJ dvobj = dobj.getRetObject("MRG61");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("XIUD50");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_XIUD50(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD50");
        rvobj.Println("XIUD50");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_XIUD50(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String NOTE_NUM ="";        //NOTE_NUM
        String   NOTE_NUM_2 = dobj.getRetObject("MRG61").getRecord().get("YRMN");   //���� ��ȣ
        String   NOTE_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� ��ȣ
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");   //�Ա� ����
        String   REPT_YRMN = dvobj.getRecord().get("YRMN");   //�Ա� ���
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //�Ű� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_NOTE (UPSO_CD, NOTE_YRMN, NOTE_NUM, REPT_DAY, REPT_NUM, REPT_GBN, USE_AMT, RECV_DAY, REMAK, INSPRES_ID, INS_DATE, BRAN_CD)  \n";
        query +=" VALUES (:UPSO_CD, :REPT_YRMN, (SELECT LPAD(NVL(MAX(NOTE_NUM), 0) + 1, 4, '0') NOTE_NUM FROM GIBU.TBRA_NOTE WHERE UPSO_CD = :NOTE_NUM_1 AND NOTE_YRMN = :NOTE_NUM_2), :REPT_DAY, :CLSED_NUM, :REPT_GBN, 0, :STTNT_DAY, :REMAK, :INSPRES_ID, SYSDATE, :BRAN_CD)";
        sobj.setSql(query);
        sobj.setString("NOTE_NUM_2", NOTE_NUM_2);               //���� ��ȣ
        sobj.setString("NOTE_NUM_1", NOTE_NUM_1);               //���� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������������
    // ����� ��� ��������� �������̺� ����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_XIUD22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD22");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_XIUD22(dobj, dvobj);
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
    private SQLObject SQLclosed_mng_head_2_XIUD22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //�Ű� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET CLSBS_YRMN = SUBSTR(:START_YRMN_1, 1, 6) , CLSBS_INS_DAY = :STTNT_DAY , MOD_DATE = SYSDATE , MODPRES_ID = :MODPRES_ID  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������������
    // ����� ��� ��������� �������̺� ����Ѵ�.
    public DOBJ CALLclosed_mng_head_2_XIUD64(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD64");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_head_2_XIUD64(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD64");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_XIUD64(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //�Ű� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET CLSBS_YRMN = SUBSTR(:START_YRMN_1, 1, 6) , CLSBS_INS_DAY = :STTNT_DAY , MOD_DATE = SYSDATE , MODPRES_ID = :MODPRES_ID  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("STTNT_DAY", STTNT_DAY);               //�Ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ä�Ǿ���üũ
    // �޾�/���������� ���� ä�ǱⰣ�� �޲����� ä�ǿϷ�� ������ �������ش�
    public DOBJ CALLclosed_mng_head_2_OSP63(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP63");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String[]  incolumns ={"GBN","UPSO_CD","COMIS","CLSED_DAY","CLSED_NUM","CLSED_GBN","DISTR_SEQ","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CLSED_NUM = dobj.getRetObject("SEL20").getRecord().get("CLSED_NUM");         //�޾� ��ȣ
            record.put("CLSED_NUM",CLSED_NUM);
            ////
            double   COMIS = 0;         //������
            record.put("COMIS",COMIS+"");
            ////
            String   DISTR_SEQ = dobj.getRetObject("MRG46").getRecord().get("DISTR_SEQ");         //�й� ����
            record.put("DISTR_SEQ",DISTR_SEQ);
            ////
            String   GBN ="I";         //����
            record.put("GBN",GBN);
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
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
        String   spname ="GIBU.SP_PROC_CLAIM_COMPN_CHECK";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.setName("OSP63");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ä�Ǿ���üũ
    // �޾�/���������� ���� ä�ǱⰣ�� �޲����� ä�ǿϷ�� ������ �������ش�
    public DOBJ CALLclosed_mng_head_2_OSP64(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP64");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String[]  incolumns ={"GBN","UPSO_CD","COMIS","CLSED_DAY","CLSED_NUM","CLSED_GBN","DISTR_SEQ","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");         //�޾� ��ȣ
            record.put("CLSED_NUM",CLSED_NUM);
            ////
            double   COMIS = 0;         //������
            record.put("COMIS",COMIS+"");
            ////
            String   DISTR_SEQ = dobj.getRetObject("MRG61").getRecord().get("DISTR_SEQ");         //�й� ����
            record.put("DISTR_SEQ",DISTR_SEQ);
            ////
            String   GBN ="U";         //����
            record.put("GBN",GBN);
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
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
        String   spname ="GIBU.SP_PROC_CLAIM_COMPN_CHECK";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.setName("OSP64");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �Աݿ�
    // �޾��� ���忡 ����� ���� ��ȸ�Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL47(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL47");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL47");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL47(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL47");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL47(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  ,  ''  AS  DISTR_SEQ  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  BETWEEN  SUBSTR(:START_YRMN_1,  1,  6)   \n";
        query +=" AND  SUBSTR(:END_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        return sobj;
    }
    // �Աݿ�
    // �޾��ϰ��� �ϴ� ���� ������ �����´�.
    public DOBJ CALLclosed_mng_head_2_SEL39(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL39");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL39");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL39(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL39");
        rvobj.Println("SEL39");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL39(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  ,  ''  AS  DISTR_SEQ  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  BETWEEN  SUBSTR(:START_YRMN_1,  1,  6)   \n";
        query +=" AND  SUBSTR(:END_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        return sobj;
    }
    // �Աݿ���Ȯ��
    // �޾��ϰ��� �ϴ� ���� ������ �Ա��� �Ǿ� �ִ��� Ȯ���Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL23");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL23");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL23(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL23");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  BETWEEN  SUBSTR(:START_YRMN_1,  1,  6)   \n";
        query +=" AND  SUBSTR(:END_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Աݿ���Ȯ��
    // �����ϰ��� �ϴ� ���� �̹� �Ա������� �ִ��� Ȯ���Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL46(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL46");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL46");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL46(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL46");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL46(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  BETWEEN  SUBSTR(:START_YRMN_1,  1,  6)   \n";
        query +=" AND  SUBSTR(:END_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �޾�������
    // �޾������� ��ȸ�Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL18");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL18");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL18(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL18");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.CLSED_DAY  ,  A.CLSED_NUM  ,  A.CLSED_BRAN  ,  A.CLSED_GBN  ,  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  A.STTNT_DAY  ,  A.REMAK  ,  B.BRAN_CD  FROM  GIBU.TBRA_UPSO_CLSED  A,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  CLSED_GBN  NOT  IN  ('14','01','02','03','04') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���������
    // ��������� ��ȸ�Ѵ�.
    public DOBJ CALLclosed_mng_head_2_SEL45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL45");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL45");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLclosed_mng_head_2_SEL45(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL45");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_head_2_SEL45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.CLSED_DAY  ,  A.CLSED_NUM  ,  A.CLSED_BRAN  ,  '14'  AS  CLSED_GBN  ,  A.CLSED_GBN  AS  GBN  ,  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  A.STTNT_DAY  ,  A.REMAK  ,  B.BRAN_CD  ,  A.ADJ_AMT  ,  A.ADJ_GBN  FROM  GIBU.TBRA_UPSO_CLSED  A,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  CLSED_GBN  IN  ('14','01','02','03','04') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$closed_mng_head_2
    //##**$$end
}
