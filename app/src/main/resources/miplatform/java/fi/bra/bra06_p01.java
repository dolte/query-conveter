
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p01
{
    public bra06_p01()
    {
    }
    //##**$$upso_info_list
    /* * ���α׷��� : bra06_p01
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/02
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_info_list(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLupso_info_list_SEL1(Conn, dobj);           //  �������ҳ���
                dobj  = CALLupso_info_list_MRG7( dobj);        //  �������
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLupso_info_list_SEL2(Conn, dobj);           //  �����߾��Ҹ���
                dobj  = CALLupso_info_list_MRG7( dobj);        //  �������
            }
            else
            {
                dobj  = CALLupso_info_list_SEL3(Conn, dobj);           //  ��ü���Ҹ���Ʈ
                dobj  = CALLupso_info_list_MRG7( dobj);        //  �������
            }
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
    public DOBJ CTLupso_info_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLupso_info_list_SEL1(Conn, dobj);           //  �������ҳ���
            dobj  = CALLupso_info_list_MRG7( dobj);        //  �������
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLupso_info_list_SEL2(Conn, dobj);           //  �����߾��Ҹ���
            dobj  = CALLupso_info_list_MRG7( dobj);        //  �������
        }
        else
        {
            dobj  = CALLupso_info_list_SEL3(Conn, dobj);           //  ��ü���Ҹ���Ʈ
            dobj  = CALLupso_info_list_MRG7( dobj);        //  �������
        }
        return dobj;
    }
    // �������ҳ���
    public DOBJ CALLupso_info_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_info_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_info_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TO_ZIP = dobj.getRetObject("S").getRecord().get("TO_ZIP");   //�����ȣ �˻� TO
        String   ADDR = dobj.getRetObject("S").getRecord().get("ADDR");   //�����ּ�
        String   FROM_ZIP = dobj.getRetObject("S").getRecord().get("FROM_ZIP");   //�����ȣ �˻� FROM
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   CHK_CONTR = dobj.getRetObject("S").getRecord().get("CHK_CONTR");   //������ üũ
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //���� ��
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT TA.BRAN_CD , GIBU.GET_BRAN_NM(TA.BRAN_CD) AS BRAN_NM , TA.UPSO_CD , TA.UPSO_NM , TA.MNGEMSTR_NM , TC.GRADNM , TC.MONPRNCFEE , TA.UPSO_NEW_ADDR1 || DECODE(TA.UPSO_NEW_ADDR2, '', '', ', ' || TA.UPSO_NEW_ADDR2) || TA.UPSO_REF_INFO AS ADDR , TA.UPSO_PHON , TB.HAN_NM AS STAFF_NM , TA.STAFF_CD , TA.UPSO_ADDR1 || DECODE(TA.UPSO_ADDR2, '', '', ', ' || TA.UPSO_ADDR2) AS SITE_ADDR , DECODE(TA.MNGEMSTR_HPNUM, NULL, TA.PERMMSTR_HPNUM, '', TA.PERMMSTR_HPNUM, ' ', TA.PERMMSTR_HPNUM, TA.MNGEMSTR_HPNUM) AS CELL_PHONNUM , TA.CONTR_NM ,(SELECT MIN (X.NOTE_YRMN)  ";
        query +=" FROM GIBU.TBRA_NOTE X  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND X.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND X.REPT_GBN BETWEEN 0  ";
        query +=" AND 8) FIRST_COL_YYMM ,(SELECT MAX (X.NOTE_YRMN)  ";
        query +=" FROM GIBU.TBRA_NOTE X  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND X.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND X.REPT_GBN BETWEEN 0  ";
        query +=" AND 8) END_COL_YYMM ,NVL ((SELECT DISTINCT '����'  ";
        query +=" FROM GIBU.TBRA_UPSO_DOC_ATTCH X  ";
        query +=" WHERE X.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND X.FILE_TYPE IN ('F3','F4','F5')), '����') CONTR_YN , (SELECT BSCONHAN_NM  ";
        query +=" FROM GIBU.TBRA_STOMU_CONTRINFO A, GIBU.TBRA_BSCON_STOMU B  ";
        query +=" WHERE A.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND A.BSCON_CD = B.BSCON_CD  ";
        query +=" AND B.USE_YN = 'Y') AS BSCON_NM , TA.BIOWN_NUM  ";
        query +=" FROM GIBU.TBRA_UPSO TA , INSA.TINS_MST01 TB , (  ";
        query +=" SELECT ZA.UPSO_CD , ZB.MONPRNCFEE , ZC.GRADNM , TRIM(ZB.BSTYP_CD) AS BSTYP_CD , ZB.UPSO_GRAD  ";
        query +=" FROM (  ";
        query +=" SELECT A.UPSO_CD , MAX(A.JOIN_SEQ) AS JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE B.BRAN_CD = DECODE( :BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" GROUP BY A.UPSO_CD ) ZA , GIBU.TBRA_UPSORTAL_INFO ZB , GIBU.TBRA_BSTYPGRAD ZC  ";
        query +=" WHERE ZB.JOIN_SEQ = ZA.JOIN_SEQ  ";
        query +=" AND ZB.UPSO_CD = ZA.UPSO_CD  ";
        query +=" AND ZC.BSTYP_CD = ZB.BSTYP_CD  ";
        query +=" AND ZC.GRAD_GBN = ZB.UPSO_GRAD ) TC  ";
        query +=" WHERE TA.BRAN_CD = DECODE( :BRAN_CD, 'AL', TA.BRAN_CD, :BRAN_CD)  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND TA.NEW_DAY <= :YRMN || '31'  ";
        query +=" AND ( TA.CLSBS_YRMN IS NULL  ";
        query +=" OR NVL(SUBSTR(TA.CLSBS_INS_DAY, 1, 6), ' ') > :YRMN)  ";
        query +=" AND TC.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND TB.STAFF_NUM(+) = TA.STAFF_CD  ";
        query +=" AND NVL(TA.STAFF_CD, ' ') LIKE :STAFF_CD || '%'  ";
        query +=" AND TC.BSTYP_CD = DECODE( :BSTYP_CD, NULL, TC.BSTYP_CD, :BSTYP_CD)  ";
        query +=" AND TA.UPSO_NEW_ZIP BETWEEN :FROM_ZIP  ";
        query +=" AND :TO_ZIP  ";
        if( !UPSO_NM.equals("") )
        {
            query +=" AND TA.UPSO_NM LIKE '%' || :UPSO_NM || '%'  ";
        }
        if( !ADDR.equals("") )
        {
            query +=" AND TA.UPSO_NEW_ADDR1 LIKE '%' || :ADDR || '%'  ";
        }
        if( !CHK_CONTR.equals("") )
        {
            query +=" AND TC.BSTYP_CD IN (SELECT CODE_CD  ";
            query +=" FROM FIDU.TENV_CODE  ";
            query +=" WHERE 1=1  ";
            query +=" AND HIGH_CD='00510'  ";
            query +=" AND USE_YN=DECODE(:CHK_CONTR,NULL,'','Y'))  ";
        }
        query +=" ORDER BY TA.UPSO_CD  ";
        sobj.setSql(query);
        sobj.setString("TO_ZIP", TO_ZIP);               //�����ȣ �˻� TO
        if(!ADDR.equals(""))
        {
            sobj.setString("ADDR", ADDR);               //�����ּ�
        }
        sobj.setString("FROM_ZIP", FROM_ZIP);               //�����ȣ �˻� FROM
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        if(!CHK_CONTR.equals(""))
        {
            sobj.setString("CHK_CONTR", CHK_CONTR);               //������ üũ
        }
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("YRMN", YRMN);               //���
        if(!UPSO_NM.equals(""))
        {
            sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �������
    public DOBJ CALLupso_info_list_MRG7(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG7");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL2, SEL3","");
        rvobj.setName("MRG7") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // �����߾��Ҹ���
    public DOBJ CALLupso_info_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_info_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_info_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TO_ZIP = dobj.getRetObject("S").getRecord().get("TO_ZIP");   //�����ȣ �˻� TO
        String   ADDR = dobj.getRetObject("S").getRecord().get("ADDR");   //�����ּ�
        String   FROM_ZIP = dobj.getRetObject("S").getRecord().get("FROM_ZIP");   //�����ȣ �˻� FROM
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   CHK_CONTR = dobj.getRetObject("S").getRecord().get("CHK_CONTR");   //������ üũ
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //���� ��
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT TA.BRAN_CD , GIBU.GET_BRAN_NM(TA.BRAN_CD) AS BRAN_NM , TA.UPSO_CD , TA.UPSO_NM , TA.MNGEMSTR_NM , TC.GRADNM , TC.MONPRNCFEE , TA.UPSO_NEW_ADDR1 || DECODE(TA.UPSO_NEW_ADDR2, '', '', ', ' || TA.UPSO_NEW_ADDR2) || TA.UPSO_REF_INFO AS ADDR , TA.UPSO_PHON , TB.HAN_NM AS STAFF_NM , TA.STAFF_CD , TA.UPSO_ADDR1 || DECODE(TA.UPSO_ADDR2, '', '', ', ' || TA.UPSO_ADDR2) AS SITE_ADDR , DECODE(TA.MNGEMSTR_HPNUM, NULL, TA.PERMMSTR_HPNUM, '', TA.PERMMSTR_HPNUM, ' ', TA.PERMMSTR_HPNUM, TA.MNGEMSTR_HPNUM) AS CELL_PHONNUM ,TA.CONTR_NM ,(SELECT MIN (X.NOTE_YRMN)  ";
        query +=" FROM GIBU.TBRA_NOTE X  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND X.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND X.REPT_GBN BETWEEN 0  ";
        query +=" AND 8) FIRST_COL_YYMM ,(SELECT MAX (X.NOTE_YRMN)  ";
        query +=" FROM GIBU.TBRA_NOTE X  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND X.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND X.REPT_GBN BETWEEN 0  ";
        query +=" AND 8) END_COL_YYMM ,NVL ((SELECT DISTINCT '����'  ";
        query +=" FROM GIBU.TBRA_UPSO_DOC_ATTCH X  ";
        query +=" WHERE X.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND X.FILE_TYPE IN ('F3','F4','F5')), '����') CONTR_YN , (SELECT BSCONHAN_NM  ";
        query +=" FROM GIBU.TBRA_STOMU_CONTRINFO A, GIBU.TBRA_BSCON_STOMU B  ";
        query +=" WHERE A.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND A.BSCON_CD = B.BSCON_CD  ";
        query +=" AND B.USE_YN = 'Y') AS BSCON_NM , TA.BIOWN_NUM  ";
        query +=" FROM GIBU.TBRA_UPSO TA , INSA.TINS_MST01 TB , (  ";
        query +=" SELECT ZA.UPSO_CD , ZB.MONPRNCFEE , ZC.GRADNM , TRIM(ZB.BSTYP_CD) AS BSTYP_CD , ZB.UPSO_GRAD  ";
        query +=" FROM (  ";
        query +=" SELECT A.UPSO_CD , MAX(A.JOIN_SEQ) AS JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE B.BRAN_CD = DECODE( :BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" GROUP BY A.UPSO_CD ) ZA , GIBU.TBRA_UPSORTAL_INFO ZB , GIBU.TBRA_BSTYPGRAD ZC  ";
        query +=" WHERE ZB.JOIN_SEQ = ZA.JOIN_SEQ  ";
        query +=" AND ZB.UPSO_CD = ZA.UPSO_CD  ";
        query +=" AND ZC.BSTYP_CD = ZB.BSTYP_CD  ";
        query +=" AND ZC.GRAD_GBN = ZB.UPSO_GRAD ) TC  ";
        query +=" WHERE TA.BRAN_CD = DECODE( :BRAN_CD, 'AL', TA.BRAN_CD, :BRAN_CD)  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND ( TA.CLSBS_YRMN IS NULL  ";
        query +=" OR NVL(SUBSTR(TA.CLSBS_INS_DAY, 1, 6), ' ') > :YRMN)  ";
        query +=" AND TC.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND TB.STAFF_NUM(+) = TA.STAFF_CD  ";
        query +=" AND NVL(TA.STAFF_CD, ' ') LIKE :STAFF_CD || '%'  ";
        query +=" AND TC.BSTYP_CD = DECODE( :BSTYP_CD, NULL, TC.BSTYP_CD, :BSTYP_CD)  ";
        query +=" AND TA.UPSO_NEW_ZIP BETWEEN :FROM_ZIP  ";
        query +=" AND :TO_ZIP  ";
        if( !UPSO_NM.equals("") )
        {
            query +=" AND TA.UPSO_NM LIKE '%' || :UPSO_NM || '%'  ";
        }
        if( !ADDR.equals("") )
        {
            query +=" AND TA.UPSO_NEW_ADDR1 LIKE '%' || :ADDR || '%'  ";
        }
        if( !CHK_CONTR.equals("") )
        {
            query +=" AND TC.BSTYP_CD IN (SELECT CODE_CD  ";
            query +=" FROM FIDU.TENV_CODE  ";
            query +=" WHERE 1=1  ";
            query +=" AND HIGH_CD='00510'  ";
            query +=" AND USE_YN=DECODE(:CHK_CONTR,NULL,'','Y'))  ";
        }
        query +=" ORDER BY TA.UPSO_CD  ";
        sobj.setSql(query);
        sobj.setString("TO_ZIP", TO_ZIP);               //�����ȣ �˻� TO
        if(!ADDR.equals(""))
        {
            sobj.setString("ADDR", ADDR);               //�����ּ�
        }
        sobj.setString("FROM_ZIP", FROM_ZIP);               //�����ȣ �˻� FROM
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        if(!CHK_CONTR.equals(""))
        {
            sobj.setString("CHK_CONTR", CHK_CONTR);               //������ üũ
        }
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("YRMN", YRMN);               //���
        if(!UPSO_NM.equals(""))
        {
            sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ��ü���Ҹ���Ʈ
    public DOBJ CALLupso_info_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_info_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_info_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TO_ZIP = dobj.getRetObject("S").getRecord().get("TO_ZIP");   //�����ȣ �˻� TO
        String   ADDR = dobj.getRetObject("S").getRecord().get("ADDR");   //�����ּ�
        String   FROM_ZIP = dobj.getRetObject("S").getRecord().get("FROM_ZIP");   //�����ȣ �˻� FROM
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   CHK_CONTR = dobj.getRetObject("S").getRecord().get("CHK_CONTR");   //������ üũ
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //���� ��
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT TA.BRAN_CD , GIBU.GET_BRAN_NM(TA.BRAN_CD) AS BRAN_NM , TA.UPSO_CD , TA.UPSO_NM , TA.MNGEMSTR_NM , TC.GRADNM , TC.MONPRNCFEE , TA.UPSO_NEW_ADDR1 || DECODE(TA.UPSO_NEW_ADDR2, '', '', ', ' || TA.UPSO_NEW_ADDR2) || TA.UPSO_REF_INFO AS ADDR , TA.UPSO_PHON , TB.HAN_NM AS STAFF_NM , TA.STAFF_CD , TA.UPSO_ADDR1 || DECODE(TA.UPSO_ADDR2, '', '', ', ' || TA.UPSO_ADDR2) AS SITE_ADDR , DECODE(TA.MNGEMSTR_HPNUM, NULL, TA.PERMMSTR_HPNUM, '', TA.PERMMSTR_HPNUM, ' ', TA.PERMMSTR_HPNUM, TA.MNGEMSTR_HPNUM) AS CELL_PHONNUM , TA.CONTR_NM ,(SELECT MIN (X.NOTE_YRMN)  ";
        query +=" FROM GIBU.TBRA_NOTE X  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND X.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND X.REPT_GBN BETWEEN 0  ";
        query +=" AND 8) FIRST_COL_YYMM ,(SELECT MAX (X.NOTE_YRMN)  ";
        query +=" FROM GIBU.TBRA_NOTE X  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND X.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND X.REPT_GBN BETWEEN 0  ";
        query +=" AND 8) END_COL_YYMM ,NVL ((SELECT DISTINCT '����'  ";
        query +=" FROM GIBU.TBRA_UPSO_DOC_ATTCH X  ";
        query +=" WHERE X.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND X.FILE_TYPE IN ('F3','F4','F5')), '����') CONTR_YN , (SELECT BSCONHAN_NM  ";
        query +=" FROM GIBU.TBRA_STOMU_CONTRINFO A, GIBU.TBRA_BSCON_STOMU B  ";
        query +=" WHERE A.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND A.BSCON_CD = B.BSCON_CD  ";
        query +=" AND B.USE_YN = 'Y') AS BSCON_NM , TA.BIOWN_NUM  ";
        query +=" FROM GIBU.TBRA_UPSO TA , INSA.TINS_MST01 TB , (  ";
        query +=" SELECT ZA.UPSO_CD , ZB.MONPRNCFEE , ZC.GRADNM , TRIM(ZB.BSTYP_CD) AS BSTYP_CD , ZB.UPSO_GRAD  ";
        query +=" FROM (  ";
        query +=" SELECT A.UPSO_CD , MAX(A.JOIN_SEQ) AS JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE B.BRAN_CD = DECODE( :BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" GROUP BY A.UPSO_CD ) ZA , GIBU.TBRA_UPSORTAL_INFO ZB , GIBU.TBRA_BSTYPGRAD ZC  ";
        query +=" WHERE ZB.JOIN_SEQ = ZA.JOIN_SEQ  ";
        query +=" AND ZB.UPSO_CD = ZA.UPSO_CD  ";
        query +=" AND ZC.BSTYP_CD = ZB.BSTYP_CD  ";
        query +=" AND ZC.GRAD_GBN = ZB.UPSO_GRAD ) TC  ";
        query +=" WHERE TA.BRAN_CD = DECODE( :BRAN_CD, 'AL', TA.BRAN_CD, :BRAN_CD)  ";
        query +=" AND (TA.NEW_DAY IS NULL  ";
        query +=" OR TA.NEW_DAY <= :YRMN || '31')  ";
        query +=" AND ( TA.CLSBS_YRMN IS NULL  ";
        query +=" OR NVL(SUBSTR(TA.CLSBS_INS_DAY, 1, 6), ' ') > :YRMN)  ";
        query +=" AND TC.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND TB.STAFF_NUM(+) = TA.STAFF_CD  ";
        query +=" AND NVL(TA.STAFF_CD, ' ') LIKE :STAFF_CD || '%'  ";
        query +=" AND TC.BSTYP_CD = DECODE( :BSTYP_CD, NULL, TC.BSTYP_CD, :BSTYP_CD)  ";
        query +=" AND TA.UPSO_NEW_ZIP BETWEEN :FROM_ZIP  ";
        query +=" AND :TO_ZIP  ";
        if( !UPSO_NM.equals("") )
        {
            query +=" AND TA.UPSO_NM LIKE '%' || :UPSO_NM || '%'  ";
        }
        if( !ADDR.equals("") )
        {
            query +=" AND TA.UPSO_NEW_ADDR1 LIKE '%' || :ADDR || '%'  ";
        }
        if( !CHK_CONTR.equals("") )
        {
            query +=" AND TC.BSTYP_CD IN (SELECT CODE_CD  ";
            query +=" FROM FIDU.TENV_CODE  ";
            query +=" WHERE 1=1  ";
            query +=" AND HIGH_CD='00510'  ";
            query +=" AND USE_YN=DECODE(:CHK_CONTR,NULL,'','Y'))  ";
        }
        query +=" ORDER BY TA.UPSO_CD  ";
        sobj.setSql(query);
        sobj.setString("TO_ZIP", TO_ZIP);               //�����ȣ �˻� TO
        if(!ADDR.equals(""))
        {
            sobj.setString("ADDR", ADDR);               //�����ּ�
        }
        sobj.setString("FROM_ZIP", FROM_ZIP);               //�����ȣ �˻� FROM
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        if(!CHK_CONTR.equals(""))
        {
            sobj.setString("CHK_CONTR", CHK_CONTR);               //������ üũ
        }
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("YRMN", YRMN);               //���
        if(!UPSO_NM.equals(""))
        {
            sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_info_list
    //##**$$end
}
