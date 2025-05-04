
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_s01
{
    public bra05_s01()
    {
    }
    //##**$$bpap_search
    /* * ���α׷��� : bra05_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/07
    * ���� :
    ������ڿ� ���� �ش� ������ �ְ� �������� ��ȸ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLbpap_search(DOBJ dobj)
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
            dobj  = CALLbpap_search_SEL1(Conn, dobj);           //  �ְ���
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
    public DOBJ CTLbpap_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbpap_search_SEL1(Conn, dobj);           //  �ְ���
        return dobj;
    }
    // �ְ���
    public DOBJ CALLbpap_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BPAP_DAY = dobj.getRetObject("S").getRecord().get("BPAP_DAY");   //�ְ� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.BRAN_CD  ,  XD.DEPT_NM  ,  DECODE(XA.BPAP_GBN,'2','�ְ�',  XA.BPAP_GBN)  BPAP_GBN  ,  XA.BPAP_DAY  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XB.MNGEMSTR_NM  ,  XE.GRADNM  ,  XC.HAN_NM  STAFF_NM  ,  XE.MONPRNCFEE  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  ADDR  ,  XA.START_YRMN||'01'  START_YRMN  ,  XA.END_YRMN||'01'  END_YRMN  ,  XA.TOT_DEMD_AMT  -  (NVL(XA.TOT_ADDT_AMT,0)  +  NVL(XA.TOT_EADDT_AMT,0))  USE_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.REMAK  ,  XA.WREC_GBN  ,  XA.DISP_DAY  FROM  GIBU.TBRA_BPAP_MNG  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TINS_MST01  XC  ,  INSA.TCPM_DEPT  XD  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XE  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XA.BPAP_DAY  =  :BPAP_DAY   \n";
        query +=" AND  XA.BPAP_GBN  =  '2'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XB.STAFF_CD   \n";
        query +=" AND  XD.GIBU  =  XB.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$bpap_search
    //##**$$bpap_init
    /* * ���α׷��� : bra05_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/01
    * ���� :
    �ְ� ����� ���� ���� �⺻ ������ û���ݾ� ������ ��ȸ�Ѵ�.
    2. OSP.OSP1 : GIBU.SP_GET_DEMD_AMT()
    - �ش������ û���ݾ��� ����Ѵ�.
    3. SEL.SEL1 : 2.OSP���� ���� û���ݾ��� ������ ���������� û���ݾ��� �����Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLbpap_init(DOBJ dobj)
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
            dobj  = CALLbpap_init_SEL2(Conn, dobj);           //  û��������ϱ�
            dobj  = CALLbpap_init_OSP1(Conn, dobj);           //  û�� �ݾ� ����
            dobj  = CALLbpap_init_SEL1(Conn, dobj);           //  ��ȸ
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
    public DOBJ CTLbpap_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbpap_init_SEL2(Conn, dobj);           //  û��������ϱ�
        dobj  = CALLbpap_init_OSP1(Conn, dobj);           //  û�� �ݾ� ����
        dobj  = CALLbpap_init_SEL1(Conn, dobj);           //  ��ȸ
        return dobj;
    }
    // û��������ϱ�
    // ���۳��, ������ ���ϱ�
    public DOBJ CALLbpap_init_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_init_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_init_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,6)  START_YRMN  ,  DECODE(A.CLSBS_YRMN,  NULL,  TO_CHAR(SYSDATE,'YYYYMM'),  A.CLSBS_YRMN)  END_YRMN  ,  :UPSO_CD  AS  UPSO_CD  ,  ''  AS  RECV_DAY  FROM  (   \n";
        query +=" SELECT  CLSBS_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =:UPSO_CD  )  A ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // û�� �ݾ� ����
    // �ش� ������ û�� �ݾ׸� �����ϱ� ���� ���ν��� (GIBU.SP_GET_USE_AMT) �� ȣ���Ѵ�
    public DOBJ CALLbpap_init_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //û��������ϱ⿡�� ������Ų OBJECT�Դϴ�.(CALLbpap_init_SEL2)
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","CRET_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRET_GBN ="O";         //û�� ���
            record.put("CRET_GBN",CRET_GBN);
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
        String   spname ="GIBU.SP_GET_DEMD_AMT";
        int[]    intypes={12, 12, 12, 12, 12};;
        String[] outcolnms={"P_BSTYP_CD","P_UPSO_GRAD","P_MONPRNCFEE","P_DEMD_GBN","P_DEMD_MMCNT","P_TUSE_AMT","P_TADDT_AMT","P_TEADDT_AMT","P_DSCT_AMT","P_TDEMD_AMT"};;
        int[]    outtypes ={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ��ȸ
    public DOBJ CALLbpap_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_init_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   P_EADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TEADDT_AMT");   //SP�߰�����÷�
        double   P_ADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TADDT_AMT");   //SP������÷�
        double   P_USE_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TUSE_AMT");   //SP�����÷�
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("SEL2").getRecord().get("END_YRMN");   //������
        String   START_YRMN = dobj.getRetObject("SEL2").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TB.BRAN_CD  ,  TD.DEPT_NM  ,  TB.UPSO_CD  ,  TB.UPSO_NM  ,  TB.MNGEMSTR_NM  ,  TE.GRADNM  ,  TC.HAN_NM  STAFF_NM  ,  TE.MONPRNCFEE  ,  TB.UPSO_NEW_ADDR1  ||  DECODE(TB.UPSO_NEW_ADDR2,  '',  '',  ',  '||TB.UPSO_NEW_ADDR2)  ||  TB.UPSO_REF_INFO  ADDR  ,  :START_YRMN||'01'  AS  START_YRMN  ,  :END_YRMN||'01'  AS  END_YRMN  ,  :P_USE_AMT  AS  USE_AMT  ,  :P_ADDT_AMT  AS  TOT_ADDT_AMT  ,  :P_EADDT_AMT  AS  TOT_EADDT_AMT  ,  TO_CHAR(SYSDATE,'YYYYMMDD')  BPAP_DAY  FROM  GIBU.TBRA_UPSO  TB  ,  INSA.TINS_MST01  TC  ,  INSA.TCPM_DEPT  TD  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TE  WHERE  TB.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  TE.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TC.STAFF_NUM(+)  =  TB.STAFF_CD   \n";
        query +=" AND  TD.GIBU  =  TB.BRAN_CD ";
        sobj.setSql(query);
        sobj.setDouble("P_EADDT_AMT", P_EADDT_AMT);               //SP�߰�����÷�
        sobj.setDouble("P_ADDT_AMT", P_ADDT_AMT);               //SP������÷�
        sobj.setDouble("P_USE_AMT", P_USE_AMT);               //SP�����÷�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$bpap_init
    //##**$$bpap_mng
    /* * ���α׷��� : bra05_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/01
    * ���� :
    �ְ� �ű� ��� �� ���� �۾��� �Ѵ�.
    1.SEL.SEL7 : �����ϰ��� �ϴ� ������ �̹� ��ϵ� �������� üũ�Ѵ�.
    �ű� �����̸� ����(3.INS.INS5)�ϰ�, ���������̸� ����(4.UPD.UPD6) �Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLbpap_mng(DOBJ dobj)
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
            dobj  = CALLbpap_mng_SEL7(Conn, dobj);           //  ���翩��Ȯ��
            if( dobj.getRetObject("SEL7").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLbpap_mng_INS5(Conn, dobj);           //  ����
            }
            else
            {
                dobj  = CALLbpap_mng_UPD6(Conn, dobj);           //  ����
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
    public DOBJ CTLbpap_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbpap_mng_SEL7(Conn, dobj);           //  ���翩��Ȯ��
        if( dobj.getRetObject("SEL7").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLbpap_mng_INS5(Conn, dobj);           //  ����
        }
        else
        {
            dobj  = CALLbpap_mng_UPD6(Conn, dobj);           //  ����
        }
        return dobj;
    }
    // ���翩��Ȯ��
    public DOBJ CALLbpap_mng_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_mng_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_mng_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BPAP_DAY = dobj.getRetObject("S").getRecord().get("BPAP_DAY");   //�ְ� ����
        String   BPAP_GBN = dobj.getRetObject("S").getRecord().get("BPAP_GBN");   //�ְ� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BPAP_MNG  WHERE  UPSO_CD  =:UPSO_CD   \n";
        query +=" AND  BPAP_DAY  =:BPAP_DAY   \n";
        query +=" AND  BPAP_GBN  =:BPAP_GBN ";
        sobj.setSql(query);
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ����
    public DOBJ CALLbpap_mng_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_mng_INS5(dobj, dvobj);
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
    private SQLObject SQLbpap_mng_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BPAP_GBN = dvobj.getRecord().get("BPAP_GBN");   //�ְ� ����
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //�ְ� ����
        String   WREC_GBN = dvobj.getRecord().get("WREC_GBN");   //���� ����
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        double   TOT_EADDT_AMT = dvobj.getRecord().getDouble("TOT_EADDT_AMT");   //�� �߰��� �ݾ�
        String   DISP_DAY = dvobj.getRecord().get("DISP_DAY");   //�߼� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   TOT_DEMD_AMT = dobj.getRetObject("S").getRecord().getDouble("USE_AMT")+dobj.getRetObject("S").getRecord().getDouble("TOT_ADDT_AMT")+dobj.getRetObject("S").getRecord().getDouble("TOT_EADDT_AMT");   //�� û�� �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BPAP_MNG (INSPRES_ID, DISP_DAY, TOT_EADDT_AMT, START_YRMN, WREC_GBN, INS_DATE, BPAP_DAY, TOT_ADDT_AMT, TOT_DEMD_AMT, BPAP_GBN, UPSO_CD, END_YRMN, REMAK)  \n";
        query +=" values(:INSPRES_ID , :DISP_DAY , :TOT_EADDT_AMT , :START_YRMN , :WREC_GBN , SYSDATE, :BPAP_DAY , :TOT_ADDT_AMT , :TOT_DEMD_AMT , :BPAP_GBN , :UPSO_CD , :END_YRMN , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("WREC_GBN", WREC_GBN);               //���� ����
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setDouble("TOT_EADDT_AMT", TOT_EADDT_AMT);               //�� �߰��� �ݾ�
        sobj.setString("DISP_DAY", DISP_DAY);               //�߼� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        return sobj;
    }
    // ����
    public DOBJ CALLbpap_mng_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_mng_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_mng_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BPAP_GBN = dvobj.getRecord().get("BPAP_GBN");   //�ְ� ����
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //�ְ� ����
        String   WREC_GBN = dvobj.getRecord().get("WREC_GBN");   //���� ����
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        double   TOT_EADDT_AMT = dvobj.getRecord().getDouble("TOT_EADDT_AMT");   //�� �߰��� �ݾ�
        String   DISP_DAY = dvobj.getRecord().get("DISP_DAY");   //�߼� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        double   TOT_DEMD_AMT = dobj.getRetObject("S").getRecord().getDouble("USE_AMT")+dobj.getRetObject("S").getRecord().getDouble("TOT_ADDT_AMT")+dobj.getRetObject("S").getRecord().getDouble("TOT_EADDT_AMT");   //�� û�� �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BPAP_MNG  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , DISP_DAY=:DISP_DAY , TOT_EADDT_AMT=:TOT_EADDT_AMT , START_YRMN=:START_YRMN , WREC_GBN=:WREC_GBN , TOT_ADDT_AMT=:TOT_ADDT_AMT , TOT_DEMD_AMT=:TOT_DEMD_AMT , MOD_DATE=SYSDATE , END_YRMN=:END_YRMN , REMAK=:REMAK  \n";
        query +=" where BPAP_DAY=:BPAP_DAY  \n";
        query +=" and BPAP_GBN=:BPAP_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("WREC_GBN", WREC_GBN);               //���� ����
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setDouble("TOT_EADDT_AMT", TOT_EADDT_AMT);               //�� �߰��� �ݾ�
        sobj.setString("DISP_DAY", DISP_DAY);               //�߼� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        return sobj;
    }
    //##**$$bpap_mng
    //##**$$use_amt
    /* * ���α׷��� : bra05_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/24
    * ���� :
    �ش� ������ û���ݾ� ������ ����Ѵ�.
    1. OSP.OSP1 : GIBU.SP_GET_DEMD_AMT()
    - �ش������ û���ݾ��� ����Ѵ�.
    2. SEL.SEL1 : 1.OSP���� ���� û���ݾ��� ������ ���������� û���ݾ��� �����Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLuse_amt(DOBJ dobj)
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
            dobj  = CALLuse_amt_SEL3(Conn, dobj);           //  RECV_DAY ����
            dobj  = CALLuse_amt_OSP1(Conn, dobj);           //  û�� �ݾ� ����
            dobj  = CALLuse_amt_SEL1(Conn, dobj);           //  �����ݾ�
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
    public DOBJ CTLuse_amt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLuse_amt_SEL3(Conn, dobj);           //  RECV_DAY ����
        dobj  = CALLuse_amt_OSP1(Conn, dobj);           //  û�� �ݾ� ����
        dobj  = CALLuse_amt_SEL1(Conn, dobj);           //  �����ݾ�
        return dobj;
    }
    // RECV_DAY ����
    //""���� �ٷ� �ѱ�� ������ ���� �߰��ܰ踦 ��
    public DOBJ CALLuse_amt_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLuse_amt_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLuse_amt_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  AS  RECV_DAY  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // û�� �ݾ� ����
    // �ش� ������ û�� �ݾ׸� �����ϱ� ���� ���ν��� (GIBU.SP_GET_USE_AMT) �� ȣ���Ѵ�
    public DOBJ CALLuse_amt_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","CRET_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRET_GBN ="O";         //û�� ���
            record.put("CRET_GBN",CRET_GBN);
            ////
            String   RECV_DAY = dobj.getRetObject("SEL3").getRecord().get("RECV_DAY");         //���� ����
            record.put("RECV_DAY",RECV_DAY);
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
        String   spname ="GIBU.SP_GET_DEMD_AMT";
        int[]    intypes={12, 12, 12, 12, 12};;
        String[] outcolnms={"P_BSTYP_CD","P_UPSO_GRAD","P_MONPRNCFEE","P_DEMD_GBN","P_DEMD_MMCNT","P_TUSE_AMT","P_TADDT_AMT","P_TEADDT_AMT","P_DSCT_AMT","P_TDEMD_AMT"};;
        int[]    outtypes ={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �����ݾ�
    public DOBJ CALLuse_amt_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLuse_amt_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLuse_amt_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TUSE_AMT = dobj.getRetObject("OSP1").getRecord().get("P_TUSE_AMT");   //TUSE_AMT
        String   TADDT_AMT = dobj.getRetObject("OSP1").getRecord().get("P_TADDT_AMT");   //TADDT_AMT
        String   TEADDT_AMT = dobj.getRetObject("OSP1").getRecord().get("P_TEADDT_AMT");   //TEADDT_AMT
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :TUSE_AMT  AS  P_USE_AMT  ,  :TADDT_AMT  AS  P_ADDT_AMT  ,  :TEADDT_AMT  AS  P_EADDT_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("TUSE_AMT", TUSE_AMT);               //TUSE_AMT
        sobj.setString("TADDT_AMT", TADDT_AMT);               //TADDT_AMT
        sobj.setString("TEADDT_AMT", TEADDT_AMT);               //TEADDT_AMT
        return sobj;
    }
    //##**$$use_amt
    //##**$$end
}
