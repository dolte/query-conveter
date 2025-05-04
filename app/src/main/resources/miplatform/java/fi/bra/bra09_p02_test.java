
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra09_p02_test
{
    public bra09_p02_test()
    {
    }
    //##**$$mon_collect_list_test
    /*
    */
    public DOBJ CTLmon_collect_list_test(DOBJ dobj)
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
            dobj  = CALLmon_collect_list_test_SEL1(Conn, dobj);           //  ��¡����Ȳ
            dobj  = CALLmon_collect_list_test_SEL2(Conn, dobj);           //  �Աݱ��к���Ȳ
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
    public DOBJ CTLmon_collect_list_test( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmon_collect_list_test_SEL1(Conn, dobj);           //  ��¡����Ȳ
        dobj  = CALLmon_collect_list_test_SEL2(Conn, dobj);           //  �Աݱ��к���Ȳ
        return dobj;
    }
    // ��¡����Ȳ
    public DOBJ CALLmon_collect_list_test_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmon_collect_list_test_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_collect_list_test_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //�Ա� ����
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.BSTYP_NM , A.MNG_CNT , A.TOT_AMT , B.COL_UPSO , B.COL_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT BSTYP_NM , COUNT(BSTYP_CD) MNG_CNT , SUM(MONPRNCFEE) TOT_AMT , 0 COL_UPSO , 0 COL_AMT , BSTYP_CD FROM(  ";
        query +=" SELECT A.UPSO_CD, B.MONPRNCFEE, B.BSTYP_CD , DECODE(TRIM(B.BSTYP_CD),'h','�ް�����','j','����','k','��������','l','�ܶ�����','m','����κ�','n','������','o','�뷡��','p','�������','q','ȣ��/�ܵ�','r','��ȭ��' , 's','�߰�����','t','���ο���','u','��������','v','�������','w','�渶','x','�����ü�','y','���ӹ�') BSTYP_NM FROM(  ";
        query +=" SELECT XB.UPSO_CD, XB.APPL_DAY, MAX(XB.CHG_NUM) CHG_NUM FROM(  ";
        query +=" SELECT A.UPSO_CD, MAX(A.APPL_DAY) APPL_DAY  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE A.APPL_DAY <= TO_CHAR(SYSDATE, 'YYYYMMDD')  ";
        query +=" AND B.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" GROUP BY A.UPSO_CD )XA , GIBU.TBRA_UPSORTAL_INFO XB  ";
        query +=" WHERE XA.UPSO_CD = XB.UPSO_CD  ";
        query +=" AND XA.APPL_DAY = XB.APPL_DAY  ";
        query +=" GROUP BY XB.UPSO_CD, XB.APPL_DAY ) A , GIBU.TBRA_UPSORTAL_INFO B , GIBU.TBRA_BSTYPGRAD C , GIBU.TBRA_UPSO D  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND B.UPSO_CD = D.UPSO_CD  ";
        query +=" AND A.APPL_DAY = B.APPL_DAY  ";
        query +=" AND A.CHG_NUM = B.CHG_NUM  ";
        query +=" AND B.BSTYP_CD = C.BSTYP_CD  ";
        query +=" AND B.UPSO_GRAD = C.GRAD_GBN  ";
        query +=" AND D.BRAN_CD = :BRAN_CD  ";
        query +=" AND D.NEW_DAY IS NOT NULL  ";
        query +=" AND D.NEW_DAY <= :YRMN ||'31'  ";
        query +=" AND D.CLSBS_DAY IS NULL ) XA  ";
        query +=" GROUP BY XA.BSTYP_CD ) A ,(  ";
        query +=" SELECT '' BSTYP_NM , 0 MNG_CNT , 0 TOT_AMT , COUNT(D.BSTYP_CD) COL_UPSO , SUM(A.USE_AMT) + SUM(A.ADDT_AMT) + SUM(A.EXT_ADDT_AMT) COL_AMT , D.BSTYP_CD  ";
        query +=" FROM GIBU.TBRA_UPSO_REPT A , FIDU.TENV_CODE B , GIBU.TBRA_UPSO C , GIBU.TBRA_UPSORTAL_INFO D  ";
        query +=" WHERE B.HIGH_CD = '00141'  ";
        query +=" AND B.CODE_ETC = 'A'  ";
        query +=" AND A.REPT_GBN = B.CODE_CD  ";
        query +=" AND C.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD  ";
        query +=" AND C.UPSO_CD = D.UPSO_CD  ";
        query +=" AND A.REPT_YRMN = :YRMN  ";
        if( !REPT_GBN.equals("") )
        {
            query +=" AND A.REPT_GBN = :REPT_GBN  ";
        }
        query +=" GROUP BY D.BSTYP_CD )B  ";
        query +=" WHERE A.BSTYP_CD = B.BSTYP_CD(+)  ";
        sobj.setSql(query);
        if(!REPT_GBN.equals(""))
        {
            sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        }
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �Աݱ��к���Ȳ
    public DOBJ CALLmon_collect_list_test_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmon_collect_list_test_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_collect_list_test_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //�Ա� ���
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //�Ա� ����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT B.CODE_NM BSTYP_NM , COUNT(A.UPSO_CD) COL_UPSO , SUM(USE_AMT) + SUM(ADDT_AMT) + SUM(EXT_ADDT_AMT) - SUM(COMIS) COL_AMT  ";
        query +=" FROM GIBU.TBRA_UPSO_REPT A , FIDU.TENV_CODE B , GIBU.TBRA_UPSO C  ";
        query +=" WHERE B.HIGH_CD = '00141'  ";
        query +=" AND B.CODE_ETC = 'A'  ";
        query +=" AND A.REPT_GBN = B.CODE_CD  ";
        query +=" AND C.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD  ";
        query +=" AND A.REPT_YRMN = :REPT_YRMN  ";
        if( !REPT_GBN.equals("") )
        {
            query +=" AND A.REPT_GBN = :REPT_GBN  ";
        }
        query +=" GROUP BY B.CODE_NM  ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        if(!REPT_GBN.equals(""))
        {
            sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$mon_collect_list_test
    //##**$$end
}
