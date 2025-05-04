
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s02
{
    public bra03_s02()
    {
    }
    //##**$$indtnpaper_init
    /* * ���α׷��� : bra03_s02
    * �ۼ��� : 999999
    * �ۼ��� : 2009/11/02
    * ����    : �������� û�� ��Ͻ� [�߰�] �̺�Ʈ�� �߻�������� ȭ�鿡 default�� ������� �� ����Ÿ�� ��ȸ�Ѵ�.
    �� ȭ�鿡�� �������ο� MICR�� �������ڸ� �ξ ���� ó���Ѵ�.
    Input :
    BRAN_CD (���� �ڵ�)
    UPSO_CD (���� �ڵ�)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLindtnpaper_init(DOBJ dobj)
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
            dobj  = CALLindtnpaper_init_SEL1(Conn, dobj);           //  �⺻����Ÿ����
            dobj  = CALLindtnpaper_init_SEL3(Conn, dobj);           //  ���ắ������
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
    public DOBJ CTLindtnpaper_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtnpaper_init_SEL1(Conn, dobj);           //  �⺻����Ÿ����
        dobj  = CALLindtnpaper_init_SEL3(Conn, dobj);           //  ���ắ������
        return dobj;
    }
    // �⺻����Ÿ����
    public DOBJ CALLindtnpaper_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLindtnpaper_init_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.MNGEMSTR_NM  ,  XB.BSTYP_CD  ,  XB.UPSO_GRAD  ,  TRIM(XB.BSTYP_CD)  ||  XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XA.STAFF_CD  ,  XE.HAN_NM  STAFF_NM  ,  XB.MONPRNCFEE  ,  XA.OPBI_DAY  ,  XA.NEW_DAY  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,  8)  LAST_YRMN  ,  XD.ACMCN_DAESU  ,  XC.END_DATE  ,  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,  8)  START_YRMN  ,  TO_CHAR(SYSDATE,'YYYYMMDD')  END_YRMN  ,  0  USE_AMT,  0  ADDT_AMT,  0  EXT_ADDT_AMT,  NULL  REMAK  ,  XA.UPSO_STAT  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  (   \n";
        query +=" SELECT  MAX(END_YEAR  ||  END_MON  ||  END_DAY)  END_DATE  FROM  GIBU.TBRA_BRANEND  WHERE  BRAN_CD  =  :BRAN_CD  )  XC  ,  (   \n";
        query +=" SELECT  SUM(A.ACMCN_DAESU)  ACMCN_DAESU  ,  B.UPSO_CD  FROM  GIBU.TBRA_UPSO_ACMCN_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD(+)  GROUP  BY  B.UPSO_CD  )  XD  ,  INSA.TINS_MST01  XE  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.STAFF_NUM(+)  =  XA.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ắ������
    public DOBJ CALLindtnpaper_init_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLindtnpaper_init_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_init_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CHG_DAY  ,  CHG_NUM  ,  CHG_BRAN  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  ,  MONPRNCFEE  ,  APPL_DAY  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =:UPSO_CD  ORDER  BY  APPL_DAY  DESC ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$indtnpaper_init
    //##**$$indtn_demd_amt
    /* * ���α׷��� : bra03_s02
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/23
    * ����    : �ش������ ���۳��, �������� ���� û���ݾ��� ����Ѵ�.
    Input
    CRET_GBN (OCR/MICR ���� ����)
    END_YRMN (������)
    START_YRMN (���۳��)
    UPSO_CD (���� �ڵ�)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLindtn_demd_amt(DOBJ dobj)
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
            dobj  = CALLindtn_demd_amt_SEL1(Conn, dobj);           //  û�������� ��ȸ
            dobj  = CALLindtn_demd_amt_OSP1(Conn, dobj);           //  û�� �ݾ� ����
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
    public DOBJ CTLindtn_demd_amt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtn_demd_amt_SEL1(Conn, dobj);           //  û�������� ��ȸ
        dobj  = CALLindtn_demd_amt_OSP1(Conn, dobj);           //  û�� �ݾ� ����
        return dobj;
    }
    // û�������� ��ȸ
    // �����ڵ�, ���۳��, �������� �������� û�������͸� ��ȸ�Ѵ�
    public DOBJ CALLindtn_demd_amt_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLindtn_demd_amt_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtn_demd_amt_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CRET_GBN = dobj.getRetObject("S").getRecord().get("CRET_GBN");   //OCR/MICR ���� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   RECV_DAY ="";   //���� ����
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  NVL(:CRET_GBN,  'O')  AS  CRET_GBN  ,  SUBSTR(:START_YRMN,  1,  6)  AS  START_YRMN  ,  SUBSTR(:END_YRMN,  1,  6)  AS  END_YRMN  ,  :RECV_DAY  AS  RECV_DAY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("CRET_GBN", CRET_GBN);               //OCR/MICR ���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("RECV_DAY", RECV_DAY);               //���� ����
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // û�� �ݾ� ����
    // �ش� ������ û�� �ݾ׸� �����ϱ� ���� ���ν��� (GIBU.SP_GET_USE_AMT) �� ȣ���Ѵ�
    public DOBJ CALLindtn_demd_amt_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL1");         //û�������� ��ȸ���� ������Ų OBJECT�Դϴ�.(CALLindtn_demd_amt_SEL1)
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
        String[] outcolnms={"BSTYP_CD","UPSO_GRAD","MONPRNCFEE","DEMD_GBN","DEMD_MMCNT","TOT_USE_AMT","TOT_ADDT_AMT","TOT_EADDT_AMT","DSCT_AMT","TOT_DEMD_AMT"};;
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
        robj.setRetcode(1);
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$indtn_demd_amt
    //##**$$rept_detail
    /* * ���α׷��� : bra03_s02
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/08
    * ����    : ��ȸ����� �������� �ش� ������ 3�Ⱓ�� ���������� ��ȸ�Ѵ�.
    Input
    UPSO_CD (���� �ڵ�)
    YEAR (�˻��⵵)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_detail(DOBJ dobj)
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
            dobj  = CALLrept_detail_SEL1(Conn, dobj);           //  ������ȸ
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
    public DOBJ CTLrept_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_detail_SEL1(Conn, dobj);           //  ������ȸ
        return dobj;
    }
    // ������ȸ
    // ��ȸ�⵵�� �������� 3���⵵ �Ա� ������ ��ȸ�Ѵ�.  1. TENV_CODE ���� �Աݱ����� ���� �����´�. �̶� �Աݱ����� HIGH_CD ���� 00147 �̴�. SQL ���� HIGH_CD  ���� CODE_CD��� �÷������� �����ؼ� ���� �����Ѵ�
    public DOBJ CALLrept_detail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_detail_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BB_YEAR ="99";        //���۳�
        String B_YEAR ="99";        //�۳�
        String   BB_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //���۳�
        String   B_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //�۳�
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //�˻��⵵
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  AA.UPSO_CD  BB_UPSO_CD  ,  AA.YRMN  BB_REPT_YRMN  ,  AA.MM  BB_MM  ,  AA.REPT_GBN  BB_REPT_GBN  ,  AA.CODE_NM  BB_CODE_NM  ,  AA.USE_AMT  BB_USE_AMT  ,  AA.REPT_DAY  BB_REPT_DAY  ,  AA.REPT_NUM  BB_REPT_NUM  ,  AA.RECV_DAY  BB_RECV_DAY  ,  AA.ACCU_GBN  BB_ACCU_GBN  ,  AA.DISTR_SEQ  BB_DISTR_SEQ  ,  AA.MAPPING_DAY  BB_MAPPING_DAY  ,  BB.UPSO_CD  B_UPSO_CD  ,  BB.YRMN  B_REPT_YRMN  ,  BB.MM  B_MM  ,  BB.REPT_GBN  B_REPT_GBN  ,  BB.CODE_NM  B_CODE_NM  ,  BB.USE_AMT  B_USE_AMT  ,  BB.REPT_DAY  B_REPT_DAY  ,  BB.REPT_NUM  B_REPT_NUM  ,  BB.RECV_DAY  B_RECV_DAY  ,  BB.ACCU_GBN  B_ACCU_GBN  ,  BB.DISTR_SEQ  B_DISTR_SEQ  ,  BB.MAPPING_DAY  B_MAPPING_DAY  ,  CC.UPSO_CD  ,  CC.YRMN  REPT_YRMN  ,  CC.MM  ,  CC.REPT_GBN  ,  CC.CODE_NM  ,  CC.USE_AMT  ,  CC.REPT_DAY  ,  CC.REPT_NUM  ,  CC.RECV_DAY  ,  CC.ACCU_GBN  ,  CC.DISTR_SEQ  ,  CC.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  'ä��',  '22',  DECODE(ACCU_SOLCNT,  1,  '����ذ�',  '��Һг�'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:BB_YEAR_1)  -  2  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  AA  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  'ä��',  '22',  DECODE(ACCU_SOLCNT,  1,  '����ذ�',  '��Һг�'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:B_YEAR_1)  -  1  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:B_YEAR_1)  -  1  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:B_YEAR_1)  -  1  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  BB  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  'ä��',  '22',  DECODE(ACCU_SOLCNT,  1,  '����ذ�',  '��Һг�'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  :YEAR  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  CC  WHERE  BB.MM  =  AA.MM   \n";
        query +=" AND  CC.MM  =  AA.MM  ORDER  BY  AA.MM ";
        sobj.setSql(query);
        sobj.setString("BB_YEAR_1", BB_YEAR_1);               //���۳�
        sobj.setString("B_YEAR_1", B_YEAR_1);               //�۳�
        sobj.setString("YEAR", YEAR);               //�˻��⵵
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_detail
    //##**$$indtnpaper_detail
    /* * ���α׷��� : bra03_s02
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/11
    * ���� : ��ϵ� ���������� ������ (���Ҹ�, û������, û���ݾ�,  ����ݾ� ��) �� ���� ������ ��ȸ�Ѵ�.
    Input
    BRAN_CD (���� �ڵ�)
    CRET_GBN (OCR/MICR ���� ����)
    DEMD_DAY (û�� ����)
    DEMD_NUM (û�� ��ȣ)
    UPSO_CD (���� �ڵ�)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLindtnpaper_detail(DOBJ dobj)
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
            dobj  = CALLindtnpaper_detail_SEL4(Conn, dobj);           //  û������
            dobj  = CALLindtnpaper_detail_SEL6(Conn, dobj);           //  ���ắ������
            dobj  = CALLindtnpaper_detail_SEL7(Conn, dobj);           //  ��¿뵥���� ��ȸ
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
    public DOBJ CTLindtnpaper_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtnpaper_detail_SEL4(Conn, dobj);           //  û������
        dobj  = CALLindtnpaper_detail_SEL6(Conn, dobj);           //  ���ắ������
        dobj  = CALLindtnpaper_detail_SEL7(Conn, dobj);           //  ��¿뵥���� ��ȸ
        return dobj;
    }
    // û������
    public DOBJ CALLindtnpaper_detail_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLindtnpaper_detail_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_detail_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //û�� ��ȣ
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //û�� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.DEMD_DAY  ,  XB.DEMD_NUM  ,  XB.BRAN_CD  ,  XA.MNGEMSTR_NM  ,  XC.BSTYP_CD  ,  XC.UPSO_GRAD  ,  TRIM(XC.BSTYP_CD)  ||  XC.UPSO_GRAD  GRAD  ,  XC.GRADNM  ,  XC.MONPRNCFEE  ,  XA.STAFF_CD  ,  XE.HAN_NM  STAFF_NM  ,  XA.OPBI_DAY  ,  XA.NEW_DAY  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XB.START_YRMN  ||  '01'  START_YRMN  ,  XB.END_YRMN  ||  '01'  END_YRMN  ,  XB.TOT_DEMD_AMT  -  XB.TOT_ADDT_AMT  -  XB.TOT_EADDT_AMT  TOT_USE_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_DEMD_AMT  ,  XB.REMAK  ,  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,  8)  LAST_YRMN  ,  XA.MCHNDAESU  ACMCN_DAESU  ,  XB.TOT_EADDT_AMT  ,  XD.END_DATE  ,   \n";
        query +=" (SELECT  DECODE(SUM(NONPY_AMT),  null,  -1,  0,  0,  1)  FROM  GIBU.TBRA_MISU_CHEKWON  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NONPY_DAY  =  :DEMD_DAY)  AS  MISU_CLOSED  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_DEMD_INDTN  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  (   \n";
        query +=" SELECT  MAX(END_YEAR  ||  END_MON  ||  END_DAY)  END_DATE  FROM  GIBU.TBRA_BRANEND  WHERE  BRAN_CD  =  :BRAN_CD  )  XD  ,  INSA.TINS_MST01  XE  WHERE  XB.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.DEMD_DAY  =  :DEMD_DAY   \n";
        query +=" AND  XB.DEMD_NUM  =  :DEMD_NUM   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XA.STAFF_CD  =  XE.STAFF_NUM(+) ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //û�� ��ȣ
        sobj.setString("DEMD_DAY", DEMD_DAY);               //û�� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ắ������
    public DOBJ CALLindtnpaper_detail_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLindtnpaper_detail_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_detail_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CHG_DAY  ,  CHG_NUM  ,  CHG_BRAN  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  ,  MONPRNCFEE  ,  APPL_DAY  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =:UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��¿뵥���� ��ȸ
    public DOBJ CALLindtnpaper_detail_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLindtnpaper_detail_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_detail_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //û������ȣ
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //û�� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.PAYPRES_GBN,  'B',  XB.MNGEMSTR_NM,  XB.PERMMSTR_NM)  PAYPRES_NM  ,  DECODE(XB.MAIL_RCPT,  'U',  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO,  'B',  XB.MNGEMSTR_NEW_ADDR1  ||  DECODE(XB.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||XB.MNGEMSTR_NEW_ADDR2)  ||  XB.MNGEMSTR_REF_INFO,  XB.PERMMSTR_NEW_ADDR1  ||  DECODE(XB.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||XB.PERMMSTR_NEW_ADDR2)  ||  XB.PERMMSTR_REF_INFO  )  UPSO_ADDR  ,  DECODE(XB.MAIL_RCPT,  'U',  XB.UPSO_NEW_ZIP,  'B',  XB.MNGEMSTR_NEW_ZIP,  XB.PERMMSTR_NEW_ZIP)  UPSO_ZIP  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  -  (XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT)  -  XA.MONPRNCFEE  NONPY_AMT  ,  XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT  TOT_ADDT_AMT  ,  XA.TOT_DEMD_AMT  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XC.BIPLC_NM  BRAN_NM  ,  XC.ADDR||'  '||XC.HNM  BRAN_ADDR  ,  XC.POST_NUM  ,  XC.PHON_NUM  BRAN_PHON  ,  XC.FAX_NUM  BRAN_FAX  ,  XD.GRADNM  ,  XA.DEMD_DAY  ,  XA.DEMD_NUM  ,  DECODE(XA.PRNT_DAY,  NULL,  0,  1)  PRINTED  ,  XB.MAIL_RCPT  ,  XB.CLIENT_NUM  ,  XA.CRET_GBN  FROM  GIBU.TBRA_DEMD_INDTN  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_BIPLC  XC  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZC.GRADNM  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XD  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.DEMD_DAY  =  :DEMD_DAY   \n";
        query +=" AND  XA.DEMD_NUM  =  :DEMD_NUM   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XD.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XB.BRAN_CD  ORDER  BY  XA.DEMD_DAY,  XA.DEMD_NUM ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //û������ȣ
        sobj.setString("DEMD_DAY", DEMD_DAY);               //û�� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$indtnpaper_detail
    //##**$$p_indtnpaper_select
    /* * ���α׷��� : bra03_s02
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/11
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLp_indtnpaper_select(DOBJ dobj)
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
            dobj  = CALLp_indtnpaper_select_SEL2(Conn, dobj);           //  üũ
            dobj  = CALLp_indtnpaper_select_SEL1(Conn, dobj);           //  ��ü����Ʈ
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
    public DOBJ CTLp_indtnpaper_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLp_indtnpaper_select_SEL2(Conn, dobj);           //  üũ
        dobj  = CALLp_indtnpaper_select_SEL1(Conn, dobj);           //  ��ü����Ʈ
        return dobj;
    }
    // üũ
    public DOBJ CALLp_indtnpaper_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLp_indtnpaper_select_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_indtnpaper_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //û�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(LENGTH(:DEMD_DAY),8,'1',7,'2',  6,'2',5,'3',4,'3','4')  GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //û�� ����
        return sobj;
    }
    // ��ü����Ʈ
    public DOBJ CALLp_indtnpaper_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLp_indtnpaper_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_indtnpaper_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //û�� ��ȣ
        String   CRET_GBN = dobj.getRetObject("S").getRecord().get("CRET_GBN");   //OCR/MICR ���� ����
        String   GBN = dobj.getRetObject("SEL2").getRecord().get("GBN");   //����
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //û�� ����
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //���� ��
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.UPSO_CD , A.UPSO_NM , B.DEMD_DAY , B.DEMD_NUM , B.BRAN_CD , B.DEMD_DAY || '-' || B.DEMD_NUM || '-' || B.BRAN_CD DEMD , A.MNGEMSTR_NM  ";
        query +=" FROM GIBU.TBRA_UPSO A , GIBU.TBRA_DEMD_INDTN B  ";
        query +=" WHERE A.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_NM LIKE '%' || :UPSO_NM || '%'  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND B.CRET_GBN = :CRET_GBN  ";
        if( GBN.equals("1"))
        {
            query +=" AND DEMD_DAY = :DEMD_DAY  ";
            query +=" AND NVL(:GBN, '-') = NVL(:GBN, '-')  ";
        }
        if( GBN.equals("2"))
        {
            query +=" AND DEMD_DAY BETWEEN SUBSTR(:DEMD_DAY,0,6) || '01'  ";
            query +=" AND SUBSTR(:DEMD_DAY,0,6) || '31'  ";
            query +=" AND NVL(:GBN, '-') = NVL(:GBN, '-')  ";
        }
        if( GBN.equals("3"))
        {
            query +=" AND DEMD_DAY LIKE SUBSTR(:DEMD_DAY,0,4) || '%'AND NVL(:GBN, '-') = NVL(:GBN, '-')  ";
        }
        query +=" AND NVL(B.DEMD_NUM, '-') LIKE DECODE(:DEMD_NUM, NULL, NVL(B.DEMD_NUM, '-'), '%' || :DEMD_NUM || '%')  ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //û�� ��ȣ
        sobj.setString("CRET_GBN", CRET_GBN);               //OCR/MICR ���� ����
        if( GBN.equals("3"))
        {
            sobj.setString("GBN", GBN);               //����
        }
        sobj.setString("DEMD_DAY", DEMD_DAY);               //û�� ����
        sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$p_indtnpaper_select
    //##**$$insert_indtn
    /*
    */
    public DOBJ CTLinsert_indtn(DOBJ dobj)
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
            dobj  = CALLinsert_indtn_MPD8(Conn, dobj);           //  �б�
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
    public DOBJ CTLinsert_indtn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLinsert_indtn_MPD8(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLinsert_indtn_MPD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD8");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( 1 == 1)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLinsert_indtn_SEL20(Conn, dobj);           //  DEMD_NUM����
                dobj  = CALLinsert_indtn_XIUD9(Conn, dobj);           //  ���
                dobj  = CALLinsert_indtn_SEL1(Conn, dobj);           //  ������
                dobj  = CALLinsert_indtn_ADD11( dobj);        //  ī��Ʈ ��
            }
        }
        return dobj;
    }
    // DEMD_NUM����
    public DOBJ CALLinsert_indtn_SEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL20");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL20");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLinsert_indtn_SEL20(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL20");
        rvobj.Println("SEL20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_indtn_SEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(DEMD_NUM),  0)  +  1,  4,  '0')  DEMD_NUM  FROM  GIBU.TBRA_DEMD_INDTN  WHERE  DEMD_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���
    public DOBJ CALLinsert_indtn_XIUD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD9");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinsert_indtn_XIUD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_indtn_XIUD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("SEL20").getRecord().get("DEMD_NUM");   //û������ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_DEMD_INDTN (DEMD_DAY , DEMD_NUM , UPSO_CD , CRET_GBN , BRAN_CD , START_YRMN , END_YRMN , DEMD_GBN , BSTYP_CD , UPSO_GRAD , MONPRNCFEE , DEMD_MMCNT , TOT_USE_AMT , TOT_ADDT_AMT , TOT_EADDT_AMT , TOT_DEMD_AMT , DSCT_AMT , REMAK , INSPRES_ID , INS_DATE ) SELECT TO_CHAR(SYSDATE, 'YYYYMMDD') AS DEMD_DAY , :DEMD_NUM , XB.UPSO_CD , 'M' AS CRET_GBN , XB.BRAN_CD , XA.START_YRMN , XA.END_YRMN , '33' AS DEMD_GBN , XA.BSTYP_CD , XA.UPSO_GRAD , XA.MONPRNCFEE , XA.DEMD_MMCNT , XA.TOT_USE_AMT , XA.TOT_ADDT_AMT , XA.TOT_EADDT_AMT , XA.TOT_DEMD_AMT , XA.DSCT_AMT , 'SMS' AS REMAK , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_DEMD_OCR XA , GIBU.TBRA_UPSO XB WHERE XB.UPSO_CD = :UPSO_CD AND XA.DEMD_YRMN = (SELECT MAX(DEMD_YRMN) FROM GIBU.TBRA_DEMD_OCR WHERE UPSO_CD = :UPSO_CD) AND XA.UPSO_CD = XB.UPSO_CD AND XA.TOT_DEMD_AMT > 0 ORDER BY XB.BRAN_CD, XB.UPSO_CD";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //û������ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������
    public DOBJ CALLinsert_indtn_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLinsert_indtn_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_indtn_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_DEMD_INDTN  WHERE  DEMD_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  REMAK  =  'SMS'   \n";
        query +=" AND  INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ī��Ʈ ��
    public DOBJ CALLinsert_indtn_ADD11(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD11");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL1");          //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        rvobj = wutil.getAddedVobj(dobj,"ADD11","CNT", dvobj );
        rvobj.setName("ADD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    //##**$$insert_indtn
    //##**$$end
}
