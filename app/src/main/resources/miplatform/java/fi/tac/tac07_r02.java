
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac07_r02
{
    public tac07_r02()
    {
    }
    //##**$$tac07_r02select2
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/17
    * ���� :
    * ������1: 2020/11/17
    * ������ :
    * �������� :���ļ����� ä���ܾ����� ����
    */
    public DOBJ CTLtac07_r02select2(DOBJ dobj)
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
            dobj  = CALLtac07_r02select2_SEL1(Conn, dobj);           //  ä��ä����ȸ
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
    public DOBJ CTLtac07_r02select2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_r02select2_SEL1(Conn, dobj);           //  ä��ä����ȸ
        return dobj;
    }
    // ä��ä����ȸ
    // ä��ä����ȸ
    public DOBJ CALLtac07_r02select2_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtac07_r02select2_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac07_r02select2_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   CLAIMDEBT_GBN = dobj.getRetObject("S").getRecord().get("CLAIMDEBT_GBN");   //ä��ä�� ����
        String   HANMB_NM = dobj.getRetObject("S").getRecord().get("HANMB_NM");   //ȸ�� �ŷ�ó �ڵ�
        String   COMPL_YN = dobj.getRetObject("S").getRecord().get("COMPL_YN");   //�Ϸ� ����
        String   REPAYPROC_START_DAY = dobj.getRetObject("S").getRecord().get("REPAYPROC_START_DAY");   //����ó�� ���� ����
        String   CLAIM_KND = dobj.getRetObject("S").getRecord().get("CLAIM_KND");   //ä�� ����
        String   REPAYPROC_END_DAY = dobj.getRetObject("S").getRecord().get("REPAYPROC_END_DAY");   //����ó�� ���� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.MB_CD, B.HANMB_NM, A.CLAIMPRES_MB_CD, A.CLAIMPRES_NM, C.CODE_NM AS CLAIMDEBT_GBN, D.CODE_NM AS CLAIM_KND, A.FIXGRD_CTENT, MAX(A.CLAIM_BST_AMT) AS CLAIM_BST_AMT, A.CLAIM_PTTNRATE, MAX(A.CLAIM_RFND_AMT) AS CLAIM_RFND_AMT, A.REPAYPROC_START_YRMN, A.REPAYPROC_END_YRMN, A.REPAYPROC_START_DAY, A.REPAYPROC_END_DAY, F.CODE_NM AS COMPL_YN, A.REMAK , G.CODE_NM AS SUPP_GBN, SUM(H.REPAY_AMT) AS REPAY_AMT, DECODE(MAX(A.CLAIM_BST_AMT),0,0,MAX(A.CLAIM_BST_AMT) - SUM(H.REPAY_AMT)) AS BAL_AMT  ";
        query +=" FROM FIDU.TMEM_CLAIMDEBT A, FIDU.TMEM_MB B, FIDU.TENV_CODE C, FIDU.TENV_CODE D, FIDU.TENV_CODE F, FIDU.TENV_CODE G, (SELECT MB_CD, MNG_NUM, SUM(REPAY_AMT) AS REPAY_AMT  ";
        query +=" FROM FIDU.TMEM_CLAIMDEBTREPAY  ";
        query +=" WHERE 1=1  ";
        query +=" AND REPAY_YRMN >=:REPAYPROC_START_DAY  ";
        query +=" AND REPAY_YRMN <=:REPAYPROC_END_DAY  ";
        query +=" GROUP BY MB_CD,MNG_NUM, REPAY_YRMN ) H  ";
        query +=" WHERE A.MB_CD = B.MB_CD  ";
        query +=" AND A.MB_CD = H.MB_CD  ";
        query +=" AND A.MNG_NUM = H.MNG_NUM  ";
        query +=" AND TRIM(A.CLAIMDEBT_GBN) = C.CODE_CD  ";
        query +=" AND '00238' = C.HIGH_CD  ";
        query +=" AND TRIM(A.CLAIM_KND) = D.CODE_CD(+)  ";
        query +=" AND '00241' = D.HIGH_CD(+)  ";
        query +=" AND A.COMPL_YN = F.CODE_CD(+)  ";
        query +=" AND '00242' = F.HIGH_CD(+)  ";
        query +=" AND A.SUPP_GBN = G.CODE_CD(+)  ";
        query +=" AND '00300' = G.HIGH_CD(+)  ";
        query +=" AND A.REPAYPROC_END_YRMN = SUBSTR(:REPAYPROC_END_DAY,1,6)  ";
        query +=" AND A.MB_CD LIKE :MB_CD ||'%'  ";
        query +=" AND B.HANMB_NM LIKE '%'||:HANMB_NM||'%'  ";
        query +=" AND A.CLAIMDEBT_GBN LIKE :CLAIMDEBT_GBN ||'%'  ";
        if( !CLAIM_KND.equals("%"))
        {
            query +=" AND A.CLAIM_KND = :CLAIM_KND  ";
        }
        query +=" AND A.COMPL_YN LIKE :COMPL_YN ||'%'  ";
        query +=" GROUP BY A.MB_CD, B.HANMB_NM, C.CODE_NM, D.CODE_NM, A.FIXGRD_CTENT, A.CLAIMPRES_MB_CD, A.CLAIMPRES_NM, A.CLAIM_PTTNRATE, A.CLAIM_RFND_AMT, A.REPAYPROC_START_YRMN, A.REPAYPROC_END_YRMN, A.REPAYPROC_START_DAY, A.REPAYPROC_END_DAY, F.CODE_NM, A.REMAK , G.CODE_NM  ";
        query +=" ORDER BY NVL(DECODE(MAX(A.CLAIM_BST_AMT),0,0,MAX(A.CLAIM_BST_AMT) - SUM(H.REPAY_AMT)),0), B.HANMB_NM  ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("CLAIMDEBT_GBN", CLAIMDEBT_GBN);               //ä��ä�� ����
        sobj.setString("HANMB_NM", HANMB_NM);               //ȸ�� �ŷ�ó �ڵ�
        sobj.setString("COMPL_YN", COMPL_YN);               //�Ϸ� ����
        sobj.setString("REPAYPROC_START_DAY", REPAYPROC_START_DAY);               //����ó�� ���� ����
        if( !CLAIM_KND.equals("%"))
        {
            sobj.setString("CLAIM_KND", CLAIM_KND);               //ä�� ����
        }
        sobj.setString("REPAYPROC_END_DAY", REPAYPROC_END_DAY);               //����ó�� ���� ����
        return sobj;
    }
    //##**$$tac07_r02select2
    //##**$$tac07_r02select
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/17
    * ���� :
    * ������1: 2020/11/17
    * ������ :
    * �������� : ���ļ����� ä���ܾ����� ����
    */
    public DOBJ CTLtac07_r02select(DOBJ dobj)
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
            dobj  = CALLtac07_r02select_SEL1(Conn, dobj);           //  ä��ä����ȸ
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
    public DOBJ CTLtac07_r02select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_r02select_SEL1(Conn, dobj);           //  ä��ä����ȸ
        return dobj;
    }
    // ä��ä����ȸ
    // ä��ä����ȸ
    public DOBJ CALLtac07_r02select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtac07_r02select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac07_r02select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   CLAIMDEBT_GBN = dobj.getRetObject("S").getRecord().get("CLAIMDEBT_GBN");   //ä��ä�� ����
        String   HANMB_NM = dobj.getRetObject("S").getRecord().get("HANMB_NM");   //ȸ�� �ŷ�ó �ڵ�
        String   COMPL_YN = dobj.getRetObject("S").getRecord().get("COMPL_YN");   //�Ϸ� ����
        String   REPAYPROC_START_DAY = dobj.getRetObject("S").getRecord().get("REPAYPROC_START_DAY");   //����ó�� ���� ����
        String   CLAIM_KND = dobj.getRetObject("S").getRecord().get("CLAIM_KND");   //ä�� ����
        String   REPAYPROC_END_DAY = dobj.getRetObject("S").getRecord().get("REPAYPROC_END_DAY");   //����ó�� ���� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.MB_CD, B.HANMB_NM, A.CLAIMPRES_MB_CD, A.CLAIMPRES_NM, C.CODE_NM AS CLAIMDEBT_GBN, D.CODE_NM AS CLAIM_KND, A.FIXGRD_CTENT, MAX(NVL(A.CLAIM_BST_AMT,0)) AS CLAIM_BST_AMT, A.CLAIM_PTTNRATE, MAX(NVL(A.CLAIM_RFND_AMT,0)) AS CLAIM_RFND_AMT, A.REPAYPROC_START_YRMN, A.REPAYPROC_END_YRMN, A.REPAYPROC_START_DAY, A.REPAYPROC_END_DAY, F.CODE_NM AS COMPL_YN, A.REMAK , G.CODE_NM AS SUPP_GBN, NVL(SUM(H.REPAY_AMT),0) AS REPAY_AMT, NVL(DECODE(MAX(A.CLAIM_BST_AMT),0,0,MAX(A.CLAIM_BST_AMT) - SUM(H.REPAY_AMT)),0) AS BAL_AMT  ";
        query +=" FROM FIDU.TMEM_CLAIMDEBT A, FIDU.TMEM_MB B, FIDU.TENV_CODE C, FIDU.TENV_CODE D, FIDU.TENV_CODE F, FIDU.TENV_CODE G, (SELECT MB_CD, MNG_NUM, SUM(REPAY_AMT) AS REPAY_AMT  ";
        query +=" FROM FIDU.TMEM_CLAIMDEBTREPAY  ";
        query +=" WHERE 1=1  ";
        query +=" AND REPAY_YRMN >=:REPAYPROC_START_DAY  ";
        query +=" AND REPAY_YRMN <=:REPAYPROC_END_DAY  ";
        query +=" GROUP BY MB_CD,MNG_NUM, REPAY_YRMN ) H  ";
        query +=" WHERE A.MB_CD = B.MB_CD  ";
        query +=" AND A.MB_CD = H.MB_CD (+)  ";
        query +=" AND A.MNG_NUM = H.MNG_NUM (+)  ";
        query +=" AND TRIM(A.CLAIMDEBT_GBN) = C.CODE_CD  ";
        query +=" AND '00238' = C.HIGH_CD  ";
        query +=" AND TRIM(A.CLAIM_KND) = D.CODE_CD(+)  ";
        query +=" AND '00241' = D.HIGH_CD(+)  ";
        query +=" AND A.COMPL_YN = F.CODE_CD(+)  ";
        query +=" AND '00242' = F.HIGH_CD(+)  ";
        query +=" AND A.SUPP_GBN = G.CODE_CD(+)  ";
        query +=" AND '00300' = G.HIGH_CD(+)  ";
        query +=" AND A.MB_CD LIKE :MB_CD ||'%'  ";
        query +=" AND B.HANMB_NM LIKE '%'||:HANMB_NM||'%'  ";
        query +=" AND A.CLAIMDEBT_GBN LIKE :CLAIMDEBT_GBN ||'%'  ";
        if( !CLAIM_KND.equals("%"))
        {
            query +=" AND A.CLAIM_KND = :CLAIM_KND  ";
        }
        query +=" AND A.COMPL_YN LIKE :COMPL_YN ||'%'  ";
        query +=" GROUP BY A.MB_CD, B.HANMB_NM, C.CODE_NM, D.CODE_NM, A.FIXGRD_CTENT, A.CLAIMPRES_MB_CD, A.CLAIMPRES_NM, A.CLAIM_PTTNRATE, A.CLAIM_RFND_AMT, A.REPAYPROC_START_YRMN, A.REPAYPROC_END_YRMN, A.REPAYPROC_START_DAY, A.REPAYPROC_END_DAY, F.CODE_NM, A.REMAK , G.CODE_NM  ";
        query +=" ORDER BY NVL(DECODE(MAX(A.CLAIM_BST_AMT),0,0,MAX(A.CLAIM_BST_AMT) - SUM(H.REPAY_AMT)),0), B.HANMB_NM  ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("CLAIMDEBT_GBN", CLAIMDEBT_GBN);               //ä��ä�� ����
        sobj.setString("HANMB_NM", HANMB_NM);               //ȸ�� �ŷ�ó �ڵ�
        sobj.setString("COMPL_YN", COMPL_YN);               //�Ϸ� ����
        sobj.setString("REPAYPROC_START_DAY", REPAYPROC_START_DAY);               //����ó�� ���� ����
        if( !CLAIM_KND.equals("%"))
        {
            sobj.setString("CLAIM_KND", CLAIM_KND);               //ä�� ����
        }
        sobj.setString("REPAYPROC_END_DAY", REPAYPROC_END_DAY);               //����ó�� ���� ����
        return sobj;
    }
    //##**$$tac07_r02select
    //##**$$end
}
