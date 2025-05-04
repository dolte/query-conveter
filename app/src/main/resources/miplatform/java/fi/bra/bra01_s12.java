
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s12
{
    public bra01_s12()
    {
    }
    //##**$$off_upso_list
    /* * ���α׷��� : bra01_s12
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/24
    * ���� : ���κ�, ��û����� �������� ����Ÿ ���ҷ� ��û�� ����Ʈ�� ��ȸ�Ѵ�.
    * ������1: 2010.07.14
    * ������ : �ǳ���
    * �������� : ��ȭ��ȣ, �ڵ��� �ʵ� �߰�.
    �޸� �ʵ� ���̺� �߰� �� ��ȸ�׸� �߰�.
    */
    public DOBJ CTLoff_upso_list(DOBJ dobj)
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
            dobj  = CALLoff_upso_list_SEL1(Conn, dobj);           //  �������ε���Ÿ��ġ������ȸ
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
    public DOBJ CTLoff_upso_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLoff_upso_list_SEL1(Conn, dobj);           //  �������ε���Ÿ��ġ������ȸ
        return dobj;
    }
    // �������ε���Ÿ��ġ������ȸ
    public DOBJ CALLoff_upso_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLoff_upso_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = wutil.substr(dobj.getRetObject("S").getRecord().get("YRMN"),0,6);   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.ESTAB_YRMN  ||'01'  ESTAB_YRMN  ,  TD.DEPT_NM  BRAN_NM  ,  TA.UPSO_CD  ,  TB.UPSO_NM  ,  TC.GRADNM  ,  TC.MONPRNCFEE  ,  TA.SEQ  ,  TA.BSCON_CD  ,  TB.UPSO_NEW_ADDR1  ||  DECODE(TB.UPSO_NEW_ADDR2,  '',  '',  ',  '||TB.UPSO_NEW_ADDR2)  ||  TB.UPSO_REF_INFO  UPSO_ADDR  ,  TB.UPSO_PHON  UPSO_PHON  ,  TB.MNGEMSTR_HPNUM  MNGEMSTR_HPNUM  ,  TA.INS_STAFF_CD  ,  TE.HAN_NM  INS_STAFF_NM  ,  TA.INS_DAY  ,  TA.COL_YN  ,  TA.COL_STAFF_CD  ,  TF.HAN_NM  COL_STAFF_NM  ,  TA.COL_DAY  ,  TB.BRAN_CD  ,  TA.ACMCN_NUM1  ,  TA.ACMCN_NUM2  ,  TA.INSTALL_LOCATION1  ,  TA.INSTALL_LOCATION2  ,  TA.MEMO  ,  TA.MODEL_NM1  ,  TA.MODEL_NM2  FROM  GIBU.TBRA_OFF_UPSO_MNG  TA  ,  GIBU.TBRA_UPSO  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TCPM_DEPT  TD  ,  INSA.TINS_MST01  TE  ,  INSA.TINS_MST01  TF  WHERE  TA.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  TB.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  TB.BRAN_CD,  'AL',  TB.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TD.GIBU  =  TB.BRAN_CD   \n";
        query +=" AND  TE.STAFF_NUM(+)  =  TA.INS_STAFF_CD   \n";
        query +=" AND  TF.STAFF_NUM(+)  =  TA.COL_STAFF_CD  ORDER  BY  TB.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$off_upso_list
    //##**$$off_upso_copy
    /* * ���α׷��� : bra01_s12
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/24
    * ���� : �ش������� ����Ÿ�� ���ǿ� �־��� ����� COPY �ؼ� �Է��Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLoff_upso_copy(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("BRAN_CD").equals("AL"))
            {
                dobj  = CALLoff_upso_copy_XIUD7(Conn, dobj);           //  ��ü���λ���
                dobj  = CALLoff_upso_copy_SEL1(Conn, dobj);           //  �����ҵ���Ÿ��ȸ
                dobj  = CALLoff_upso_copy_INS2(Conn, dobj);           //  ����Ÿ����
                dobj  = CALLoff_upso_copy_SEL3(Conn, dobj);           //  ��������ȸ
            }
            else
            {
                dobj  = CALLoff_upso_copy_XIUD5(Conn, dobj);           //  ��������Ÿ����
                dobj  = CALLoff_upso_copy_SEL1(Conn, dobj);           //  �����ҵ���Ÿ��ȸ
                dobj  = CALLoff_upso_copy_INS2(Conn, dobj);           //  ����Ÿ����
                dobj  = CALLoff_upso_copy_SEL3(Conn, dobj);           //  ��������ȸ
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
    public DOBJ CTLoff_upso_copy( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("BRAN_CD").equals("AL"))
        {
            dobj  = CALLoff_upso_copy_XIUD7(Conn, dobj);           //  ��ü���λ���
            dobj  = CALLoff_upso_copy_SEL1(Conn, dobj);           //  �����ҵ���Ÿ��ȸ
            dobj  = CALLoff_upso_copy_INS2(Conn, dobj);           //  ����Ÿ����
            dobj  = CALLoff_upso_copy_SEL3(Conn, dobj);           //  ��������ȸ
        }
        else
        {
            dobj  = CALLoff_upso_copy_XIUD5(Conn, dobj);           //  ��������Ÿ����
            dobj  = CALLoff_upso_copy_SEL1(Conn, dobj);           //  �����ҵ���Ÿ��ȸ
            dobj  = CALLoff_upso_copy_INS2(Conn, dobj);           //  ����Ÿ����
            dobj  = CALLoff_upso_copy_SEL3(Conn, dobj);           //  ��������ȸ
        }
        return dobj;
    }
    // ��ü���λ���
    public DOBJ CALLoff_upso_copy_XIUD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD7");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_copy_XIUD7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_copy_XIUD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_OFF_UPSO_MNG  \n";
        query +=" WHERE ESTAB_YRMN = :TO_YRMN";
        sobj.setSql(query);
        sobj.setString("TO_YRMN", TO_YRMN);               //������
        return sobj;
    }
    // �����ҵ���Ÿ��ȸ
    // �����ؿ� ����Ʈ�� ��ȸ�Ѵ�.
    public DOBJ CALLoff_upso_copy_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLoff_upso_copy_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_copy_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //���۳��
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.ESTAB_YRMN  ,  A.UPSO_CD  ,  A.SEQ  ,  A.BSCON_CD  ,  A.COL_YN  ,  A.INSTALL_LOCATION1  ,  A.INSTALL_LOCATION2  ,  A.MODEL_NM1  ,  A.MODEL_NM2  ,  B.STAFF_CD  ,  A.ACMCN_NUM1  ,  A.ACMCN_NUM2  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.ESTAB_YRMN  =  :FROM_YRMN   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //���۳��
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ����Ÿ����
    // ����Ÿ�� �����Ұ�� 1. INS_STAFF_CD�� INS_DAY ���� ���� �α����� ����� ID�� SYSDATE�� �Է����ش�. -----------> ���� ������ �ɼ��� �ִ�.(INS_STAFF_CD�� �ش� ������ ����� ID�� �ɼ� ������ ���) 2. �������� �������, �����ϱ��� �����Ѵ�. 3. �ǳ���. 2010.06.04    ����� ��������, ������, ��������� �����󿡼� �����Ѵ�.
    public DOBJ CALLoff_upso_copy_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("SEL1");           //�����ҵ���Ÿ��ȸ���� ������Ų OBJECT�Դϴ�.(CALLoff_upso_copy_SEL1)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_copy_INS2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_copy_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String INS_DAY ="";        //��� ����
        String   ACMCN_NUM2 = dvobj.getRecord().get("ACMCN_NUM2");   //���ֱ�� ��ȣ2
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   ACMCN_NUM1 = dvobj.getRecord().get("ACMCN_NUM1");   //���ֱ�� ��ȣ1
        String   INSTALL_LOCATION1 = dvobj.getRecord().get("INSTALL_LOCATION1");
        String   MODEL_NM1 = dvobj.getRecord().get("MODEL_NM1");   //�𵨸�1
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   INSTALL_LOCATION2 = dvobj.getRecord().get("INSTALL_LOCATION2");
        String   MODEL_NM2 = dvobj.getRecord().get("MODEL_NM2");   //�𵨸�2
        String   ESTAB_YRMN = wutil.substr(dobj.getRetObject("S").getRecord().get("TO_YRMN"),0,6);   //��ġ ���
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   INS_STAFF_CD = dobj.getRetObject("SEL1").getRecord().get("STAFF_CD");   //��� ��� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_OFF_UPSO_MNG (ESTAB_YRMN, MODEL_NM2, INSPRES_ID, INSTALL_LOCATION2, INS_STAFF_CD, INS_DAY, SEQ, BSCON_CD, INS_DATE, MODEL_NM1, INSTALL_LOCATION1, ACMCN_NUM1, UPSO_CD, ACMCN_NUM2)  \n";
        query +=" values(:ESTAB_YRMN , :MODEL_NM2 , :INSPRES_ID , :INSTALL_LOCATION2 , :INS_STAFF_CD , TO_CHAR(SYSDATE, 'YYYYMMDD'), :SEQ , :BSCON_CD , SYSDATE, :MODEL_NM1 , :INSTALL_LOCATION1 , :ACMCN_NUM1 , :UPSO_CD , :ACMCN_NUM2 )";
        sobj.setSql(query);
        sobj.setString("ACMCN_NUM2", ACMCN_NUM2);               //���ֱ�� ��ȣ2
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("ACMCN_NUM1", ACMCN_NUM1);               //���ֱ�� ��ȣ1
        sobj.setString("INSTALL_LOCATION1", INSTALL_LOCATION1);
        sobj.setString("MODEL_NM1", MODEL_NM1);               //�𵨸�1
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("INSTALL_LOCATION2", INSTALL_LOCATION2);
        sobj.setString("MODEL_NM2", MODEL_NM2);               //�𵨸�2
        sobj.setString("ESTAB_YRMN", ESTAB_YRMN);               //��ġ ���
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("INS_STAFF_CD", INS_STAFF_CD);               //��� ��� �ڵ�
        return sobj;
    }
    // ��������ȸ
    // ����� ����� ��ȸ�Ѵ�.
    public DOBJ CALLoff_upso_copy_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLoff_upso_copy_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_copy_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.ESTAB_YRMN  ||'01'  ESTAB_YRMN  ,  TD.DEPT_NM  BRAN_NM  ,  TA.UPSO_CD  ,  TB.UPSO_NM  ,  TC.GRADNM  ,  TA.SEQ  ,  TA.BSCON_CD  ,  TA.INS_STAFF_CD  ,  TE.HAN_NM  INS_STAFF_NM  ,  TA.INS_DAY  ,  DECODE(TA.COL_YN  ,  'Y',  '1')  COL_YN  ,  TA.COL_STAFF_CD  ,  TF.HAN_NM  COL_STAFF_NM  ,  TA.COL_DAY  ,  TB.BRAN_CD  ,  TB.UPSO_NEW_ADDR1  ||  DECODE(TB.UPSO_NEW_ADDR2,  '',  '',  ',  '||TB.UPSO_NEW_ADDR2)  ||  TB.UPSO_REF_INFO  UPSO_ADDR  ,  TA.INSTALL_LOCATION1  ,  TA.INSTALL_LOCATION2  ,  TA.ACMCN_NUM1  ,  TA.ACMCN_NUM2  ,  TA.MODEL_NM1  ,  TA.MODEL_NM2  ,  TB.UPSO_PHON  UPSO_PHON  ,  TB.MNGEMSTR_HPNUM  MNGEMSTR_HPNUM  FROM  GIBU.TBRA_OFF_UPSO_MNG  TA  ,  GIBU.TBRA_UPSO  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TCPM_DEPT  TD  ,  INSA.TINS_MST01  TE  ,  INSA.TINS_MST01  TF  WHERE  TA.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  TB.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  TB.BRAN_CD,  'AL',  TB.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TD.GIBU  =  TB.BRAN_CD   \n";
        query +=" AND  TE.STAFF_NUM(+)  =  TA.INS_STAFF_CD   \n";
        query +=" AND  TF.STAFF_NUM(+)  =  TA.COL_STAFF_CD  ORDER  BY  TB.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ��������Ÿ����
    // �ش����� ��������Ÿ�� �����Ѵ�.
    public DOBJ CALLoff_upso_copy_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("XIUD5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_copy_XIUD5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_copy_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_OFF_UPSO_MNG  \n";
        query +=" WHERE ESTAB_YRMN = :TO_YRMN  \n";
        query +=" AND UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE BRAN_CD = DECODE(:BRAN_CD, null, BRAN_CD, 'AL', BRAN_CD, :BRAN_CD))";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("TO_YRMN", TO_YRMN);               //������
        return sobj;
    }
    //##**$$off_upso_copy
    //##**$$off_upso_dup_chk
    /* * ���α׷��� : bra01_s12
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/24
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLoff_upso_dup_chk(DOBJ dobj)
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
            dobj  = CALLoff_upso_dup_chk_SEL1(Conn, dobj);           //  ����������Ÿ�ߺ�üũ
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
    public DOBJ CTLoff_upso_dup_chk( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLoff_upso_dup_chk_SEL1(Conn, dobj);           //  ����������Ÿ�ߺ�üũ
        return dobj;
    }
    // ����������Ÿ�ߺ�üũ
    public DOBJ CALLoff_upso_dup_chk_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLoff_upso_dup_chk_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_dup_chk_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TO_YRMN = wutil.substr(dobj.getRetObject("S").getRecord().get("TO_YRMN"),0,6);   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.ESTAB_YRMN  =  :TO_YRMN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("TO_YRMN", TO_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$off_upso_dup_chk
    //##**$$off_upso_mng
    /* * ���α׷��� : bra01_s12
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/02
    * ���� : �������ε���Ÿ ��ġ���ҵ���Ÿ�� �űԵ��/����/�����Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLoff_upso_mng(DOBJ dobj)
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
            dobj  = CALLoff_upso_mng_MIUD1(Conn, dobj);           //  �Ǻ�ó��
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
    public DOBJ CTLoff_upso_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLoff_upso_mng_MIUD1(Conn, dobj);           //  �Ǻ�ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �Ǻ�ó��
    public DOBJ CALLoff_upso_mng_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLoff_upso_mng_INS5(Conn, dobj);           //  ����
                dobj  = CALLoff_upso_mng_INS9(Conn, dobj);           //  KDS_SHOP�� INSERT
                dobj  = CALLoff_upso_mng_INS10(Conn, dobj);           //  KDS_SHOPROOM�� INSERT
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLoff_upso_mng_UPD6(Conn, dobj);           //  ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLoff_upso_mng_DEL7(Conn, dobj);           //  ����
                dobj  = CALLoff_upso_mng_DEL13(Conn, dobj);           //  KDS_SHOP�� DELETE
                dobj  = CALLoff_upso_mng_DEL14(Conn, dobj);           //  KDS_SHOPROOM�� DELETE
            }
        }
        return dobj;
    }
    // ����
    // ��������Ÿ�� �����Ѵ�
    public DOBJ CALLoff_upso_mng_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_mng_DEL7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_mng_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ESTAB_YRMN ="";        //��ġ ���
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   ESTAB_YRMN_1 = dobj.getRetObject("R").getRecord().get("ESTAB_YRMN");   //��ġ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_OFF_UPSO_MNG  \n";
        query +=" where ESTAB_YRMN=substr(:ESTAB_YRMN_1, 0, 6)  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("ESTAB_YRMN_1", ESTAB_YRMN_1);               //��ġ ���
        return sobj;
    }
    // ����
    // �űԵ���Ÿ�� ����Ѵ�
    public DOBJ CALLoff_upso_mng_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_mng_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_mng_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ESTAB_YRMN ="";        //��ġ ���
        String INS_DATE ="";        //��� �Ͻ�
        String   ACMCN_NUM2 = dvobj.getRecord().get("ACMCN_NUM2");   //���ֱ�� ��ȣ2
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   COL_DAY = dvobj.getRecord().get("COL_DAY");   //���� ����
        String   COL_STAFF_CD = dvobj.getRecord().get("COL_STAFF_CD");   //���� ��� �ڵ�
        String   ACMCN_NUM1 = dvobj.getRecord().get("ACMCN_NUM1");   //���ֱ�� ��ȣ1
        String   MEMO = dvobj.getRecord().get("MEMO");   //�޸�
        String   INSTALL_LOCATION1 = dvobj.getRecord().get("INSTALL_LOCATION1");
        String   MODEL_NM1 = dvobj.getRecord().get("MODEL_NM1");   //�𵨸�1
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        String   INS_STAFF_CD = dvobj.getRecord().get("INS_STAFF_CD");   //��� ��� �ڵ�
        String   INSTALL_LOCATION2 = dvobj.getRecord().get("INSTALL_LOCATION2");
        String   MODEL_NM2 = dvobj.getRecord().get("MODEL_NM2");   //�𵨸�2
        String   COL_YN = dvobj.getRecord().get("COL_YN");   //���� ����
        String   ESTAB_YRMN_1 = dobj.getRetObject("R").getRecord().get("ESTAB_YRMN");   //��ġ ���
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_OFF_UPSO_MNG (ESTAB_YRMN, COL_YN, MODEL_NM2, INSPRES_ID, INSTALL_LOCATION2, INS_STAFF_CD, INS_DAY, SEQ, BSCON_CD, MODEL_NM1, INS_DATE, INSTALL_LOCATION1, MEMO, ACMCN_NUM1, COL_STAFF_CD, COL_DAY, UPSO_CD, ACMCN_NUM2)  \n";
        query +=" values(substr(:ESTAB_YRMN_1, 0, 6), :COL_YN , :MODEL_NM2 , :INSPRES_ID , :INSTALL_LOCATION2 , :INS_STAFF_CD , :INS_DAY , :SEQ , :BSCON_CD , :MODEL_NM1 , SYSDATE, :INSTALL_LOCATION1 , :MEMO , :ACMCN_NUM1 , :COL_STAFF_CD , :COL_DAY , :UPSO_CD , :ACMCN_NUM2 )";
        sobj.setSql(query);
        sobj.setString("ACMCN_NUM2", ACMCN_NUM2);               //���ֱ�� ��ȣ2
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("COL_DAY", COL_DAY);               //���� ����
        sobj.setString("COL_STAFF_CD", COL_STAFF_CD);               //���� ��� �ڵ�
        sobj.setString("ACMCN_NUM1", ACMCN_NUM1);               //���ֱ�� ��ȣ1
        sobj.setString("MEMO", MEMO);               //�޸�
        sobj.setString("INSTALL_LOCATION1", INSTALL_LOCATION1);
        sobj.setString("MODEL_NM1", MODEL_NM1);               //�𵨸�1
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("INS_STAFF_CD", INS_STAFF_CD);               //��� ��� �ڵ�
        sobj.setString("INSTALL_LOCATION2", INSTALL_LOCATION2);
        sobj.setString("MODEL_NM2", MODEL_NM2);               //�𵨸�2
        sobj.setString("COL_YN", COL_YN);               //���� ����
        sobj.setString("ESTAB_YRMN_1", ESTAB_YRMN_1);               //��ġ ���
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ����
    // ��������Ÿ�� �����Ѵ�
    public DOBJ CALLoff_upso_mng_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_mng_UPD6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        rvobj.Println("UPD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_mng_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ESTAB_YRMN ="";        //��ġ ���
        String MOD_DATE ="";        //���� �Ͻ�
        String   ACMCN_NUM2 = dvobj.getRecord().get("ACMCN_NUM2");   //���ֱ�� ��ȣ2
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   COL_DAY = dvobj.getRecord().get("COL_DAY");   //���� ����
        String   COL_STAFF_CD = dvobj.getRecord().get("COL_STAFF_CD");   //���� ��� �ڵ�
        String   ACMCN_NUM1 = dvobj.getRecord().get("ACMCN_NUM1");   //���ֱ�� ��ȣ1
        String   MEMO = dvobj.getRecord().get("MEMO");   //�޸�
        String   INSTALL_LOCATION1 = dvobj.getRecord().get("INSTALL_LOCATION1");
        String   MODEL_NM1 = dvobj.getRecord().get("MODEL_NM1");   //�𵨸�1
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        String   INS_STAFF_CD = dvobj.getRecord().get("INS_STAFF_CD");   //��� ��� �ڵ�
        String   INSTALL_LOCATION2 = dvobj.getRecord().get("INSTALL_LOCATION2");
        String   MODEL_NM2 = dvobj.getRecord().get("MODEL_NM2");   //�𵨸�2
        String   COL_YN = dvobj.getRecord().get("COL_YN");   //���� ����
        String   ESTAB_YRMN_1 = dobj.getRetObject("R").getRecord().get("ESTAB_YRMN");   //��ġ ���
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_OFF_UPSO_MNG  \n";
        query +=" set COL_YN=:COL_YN , MODPRES_ID=:MODPRES_ID , MODEL_NM2=:MODEL_NM2 , INSTALL_LOCATION2=:INSTALL_LOCATION2 , INS_STAFF_CD=:INS_STAFF_CD , INS_DAY=:INS_DAY , BSCON_CD=:BSCON_CD , MODEL_NM1=:MODEL_NM1 , INSTALL_LOCATION1=:INSTALL_LOCATION1 , MEMO=:MEMO , ACMCN_NUM1=:ACMCN_NUM1 , COL_STAFF_CD=:COL_STAFF_CD , COL_DAY=:COL_DAY , ACMCN_NUM2=:ACMCN_NUM2 , MOD_DATE=SYSDATE  \n";
        query +=" where ESTAB_YRMN=substr(:ESTAB_YRMN_1, 0, 6)  \n";
        query +=" and SEQ=:SEQ  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("ACMCN_NUM2", ACMCN_NUM2);               //���ֱ�� ��ȣ2
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("COL_DAY", COL_DAY);               //���� ����
        sobj.setString("COL_STAFF_CD", COL_STAFF_CD);               //���� ��� �ڵ�
        sobj.setString("ACMCN_NUM1", ACMCN_NUM1);               //���ֱ�� ��ȣ1
        sobj.setString("MEMO", MEMO);               //�޸�
        sobj.setString("INSTALL_LOCATION1", INSTALL_LOCATION1);
        sobj.setString("MODEL_NM1", MODEL_NM1);               //�𵨸�1
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("INS_STAFF_CD", INS_STAFF_CD);               //��� ��� �ڵ�
        sobj.setString("INSTALL_LOCATION2", INSTALL_LOCATION2);
        sobj.setString("MODEL_NM2", MODEL_NM2);               //�𵨸�2
        sobj.setString("COL_YN", COL_YN);               //���� ����
        sobj.setString("ESTAB_YRMN_1", ESTAB_YRMN_1);               //��ġ ���
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // KDS_SHOP�� DELETE
    public DOBJ CALLoff_upso_mng_DEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_mng_DEL13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_mng_DEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from LOG.KDS_SHOP  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // KDS_SHOP�� INSERT
    public DOBJ CALLoff_upso_mng_INS9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_mng_INS9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_mng_INS9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOP (MODPRES_ID, UPSO_CD, MOD_DATE)  \n";
        query +=" values(:MODPRES_ID , :UPSO_CD , SYSDATE)";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // KDS_SHOPROOM�� DELETE
    public DOBJ CALLoff_upso_mng_DEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_mng_DEL14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_mng_DEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from LOG.KDS_SHOPROOM  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // KDS_SHOPROOM�� INSERT
    public DOBJ CALLoff_upso_mng_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_mng_INS10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_mng_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CO_STATUS ="07001";   //�����
        String   ROOM_NAME ="�ӽù��ȣ";   //�� �̸�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOPROOM (CO_STATUS, UPSO_CD, SEQ, REG_DATE, BSCON_CD, ROOM_NAME)  \n";
        query +=" values(:CO_STATUS , :UPSO_CD , LOG.IMT_KDSSR_SEQ.NEXTVAL, SYSDATE, :BSCON_CD , :ROOM_NAME )";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CO_STATUS", CO_STATUS);               //�����
        sobj.setString("ROOM_NAME", ROOM_NAME);               //�� �̸�
        return sobj;
    }
    //##**$$off_upso_mng
    //##**$$excel_save_list
    /*
    */
    public DOBJ CTLexcel_save_list(DOBJ dobj)
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
            dobj  = CALLexcel_save_list_SEL1(Conn, dobj);           //  �������帮��Ʈ
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
    public DOBJ CTLexcel_save_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLexcel_save_list_SEL1(Conn, dobj);           //  �������帮��Ʈ
        return dobj;
    }
    // �������帮��Ʈ
    public DOBJ CALLexcel_save_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLexcel_save_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLexcel_save_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.SAVE_DAY  ,  A.SAVE_SEQ  ,  A.STAFF_CD  ,  B.HAN_NM  STAFF_NM  ,  C.DEPT_CD  ,  C.DEPT_NM  FROM  GIBU.TBRA_OFF_UPSO_SAVE_HISTY  A  ,  INSA.TINS_MST01  B  ,  INSA.TCPM_DEPT  C  WHERE  A.STAFF_CD  =  B.STAFF_NUM   \n";
        query +=" AND  A.DEPT_CD  =  B.DEPT_CD   \n";
        query +=" AND  B.DEPT_CD  =  C.DEPT_CD  ORDER  BY  A.SAVE_DAY  DESC,  A.SAVE_SEQ  ASC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$excel_save_list
    //##**$$off_upso_save_histy
    /* * ���α׷��� : bra01_s12
    * �ۼ��� : ������
    * �ۼ��� : 2010/05/23
    * ���� : �������� ��ġ���� �������� �̷�
    ����, ���� �����ߴ��� �̷��� �����Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLoff_upso_save_histy(DOBJ dobj)
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
            dobj  = CALLoff_upso_save_histy_SEL2(Conn, dobj);           //  SAVE_SEQ���ϱ�
            dobj  = CALLoff_upso_save_histy_INS1(Conn, dobj);           //  ���������̷�
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
    public DOBJ CTLoff_upso_save_histy( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLoff_upso_save_histy_SEL2(Conn, dobj);           //  SAVE_SEQ���ϱ�
        dobj  = CALLoff_upso_save_histy_INS1(Conn, dobj);           //  ���������̷�
        return dobj;
    }
    // SAVE_SEQ���ϱ�
    public DOBJ CALLoff_upso_save_histy_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLoff_upso_save_histy_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_save_histy_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SAVE_SEQ),0)+1  SAVE_SEQ  FROM  GIBU.TBRA_OFF_UPSO_SAVE_HISTY  WHERE  SAVE_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD') ";
        sobj.setSql(query);
        return sobj;
    }
    // ���������̷�
    public DOBJ CALLoff_upso_save_histy_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_save_histy_INS1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_save_histy_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String SAVE_DAY ="";        //���� ����
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   DEPT_CD = dvobj.getRecord().get("DEPT_CD");   //�μ� �ڵ�
        double   SAVE_SEQ = dobj.getRetObject("SEL2").getRecord().getDouble("SAVE_SEQ");   //���� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_OFF_UPSO_SAVE_HISTY (DEPT_CD, STAFF_CD, SAVE_DAY, SAVE_SEQ)  \n";
        query +=" values(:DEPT_CD , :STAFF_CD , TO_CHAR(SYSDATE, 'YYYYMMDD'), :SAVE_SEQ )";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("DEPT_CD", DEPT_CD);               //�μ� �ڵ�
        sobj.setDouble("SAVE_SEQ", SAVE_SEQ);               //���� ����
        return sobj;
    }
    //##**$$off_upso_save_histy
    //##**$$off_upso_stat
    /*
    */
    public DOBJ CTLoff_upso_stat(DOBJ dobj)
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
            dobj  = CALLoff_upso_stat_SEL1(Conn, dobj);           //  �������
            dobj  = CALLoff_upso_stat_SEL2(Conn, dobj);           //  ���
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
    public DOBJ CTLoff_upso_stat( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLoff_upso_stat_SEL1(Conn, dobj);           //  �������
        dobj  = CALLoff_upso_stat_SEL2(Conn, dobj);           //  ���
        return dobj;
    }
    // �������
    public DOBJ CALLoff_upso_stat_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLoff_upso_stat_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_stat_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD,  BRAN_NM  ,  :YRMN  AS  YRMN,  '  '  AS  MEMO  ,  SUM(DECODE(GRAD,  'o',  DECODE(BSCON_CD,  'E0006',  1,  0),  0))  AS  VAL_ALL_O_KY  ,  SUM(DECODE(GRAD,  'o',  DECODE(BSCON_CD,  'E0003',  1,  0),  0))  AS  VAL_ALL_O_TJ  ,  SUM(DECODE(GRAD,  'k',  DECODE(BSCON_CD,  'E0006',  1,  0),  0))  AS  VAL_ALL_K_KY  ,  SUM(DECODE(GRAD,  'k',  DECODE(BSCON_CD,  'E0003',  1,  0),  0))  AS  VAL_ALL_K_TJ  ,  SUM(DECODE(GRAD,  'l',  DECODE(BSCON_CD,  'E0006',  1,  0),  0))  AS  VAL_ALL_L_KY  ,  SUM(DECODE(GRAD,  'l',  DECODE(BSCON_CD,  'E0003',  1,  0),  0))  AS  VAL_ALL_L_TJ  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'o',  DECODE(BSCON_CD,  'E0006',  1,  0),  0),  0))  AS  VAL_COL_O_KY  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'o',  DECODE(BSCON_CD,  'E0003',  1,  0),  0),  0))  AS  VAL_COL_O_TJ  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'k',  DECODE(BSCON_CD,  'E0006',  1,  0),  0),  0))  AS  VAL_COL_K_KY  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'k',  DECODE(BSCON_CD,  'E0003',  1,  0),  0),  0))  AS  VAL_COL_K_TJ  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'l',  DECODE(BSCON_CD,  'E0006',  1,  0),  0),  0))  AS  VAL_COL_L_KY  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'l',  DECODE(BSCON_CD,  'E0003',  1,  0),  0),  0))  AS  VAL_COL_L_TJ  FROM   \n";
        query +=" (SELECT  B.BRAN_CD  ,  C.DEPT_NM  AS  BRAN_NM  ,  A.UPSO_CD  ,  A.COL_YN  ,  A.BSCON_CD  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,1)  GRAD  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_DEPT  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  C.GIBU  =  B.BRAN_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN)  GROUP  BY  BRAN_CD,  BRAN_NM  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    // ���
    public DOBJ CALLoff_upso_stat_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLoff_upso_stat_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_stat_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'A'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_A,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'B'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_B,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'E'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_E,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'F'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_F,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'G'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_G,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'I'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_I,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'K'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_K,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'L'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_L,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'M'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_M,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'N'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_N,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'O'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_O  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    //##**$$off_upso_stat
    //##**$$end
}
