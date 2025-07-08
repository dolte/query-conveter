
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r19
{
    public tac10_r19()
    {
    }
    //##**$$searchMst
    /* * ���α׷��� : tac10_r19
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/11/26
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLsearchMst(DOBJ dobj)
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
            dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  �ι��� ��׼� ��ǰ����ȸ
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
    public DOBJ CTLsearchMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  �ι��� ��׼� ��ǰ����ȸ
        return dobj;
    }
    // �ι��� ��׼� ��ǰ����ȸ
    public DOBJ CALLsearchMst_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearchMst_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //�˻��⵵
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   SECT_CD = dobj.getRetObject("S").getRecord().get("SECT_CD");   //�ι� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT MB_CD, HANMB_NM, SN, INS_NUM, SUBSTR(INS_NUM,1,7)||'******' AS INS_NUM2, ENTRN_DAY, STAMT1, STAMT2, STAMT3, TOT_ORGDISTR_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT A.MB_CD AS MB_CD, MAX(A.HANMB_NM) HANMB_NM, MAX(B.SN) SN, MAX(A.INS_NUM) INS_NUM, FIDU.GET_MB_START_DAY( A.MB_CD, 'E') AS ENTRN_DAY, NVL(SUM((CASE WHEN SUBSTR(C.SUPP_YRMN,1,4) = TO_NUMBER(:YEAR)-1 THEN C.TOT_ORGDISTR_AMT END)),0) AS STAMT1, NVL(SUM((CASE WHEN SUBSTR(C.SUPP_YRMN,1,4) = TO_NUMBER(:YEAR)-2 THEN C.TOT_ORGDISTR_AMT END)),0) AS STAMT2, NVL(SUM((CASE WHEN SUBSTR(C.SUPP_YRMN,1,4) = TO_NUMBER(:YEAR)-3 THEN C.TOT_ORGDISTR_AMT END)),0) AS STAMT3, SUM(C.TOT_ORGDISTR_AMT) AS TOT_ORGDISTR_AMT  ";
        query +=" FROM FIDU.TMEM_MB A, FIDU.TMEM_SN B , FIDU.TTAC_COPYRTAL C  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND A.MB_CD = B.MB_CD(+)  ";
        query +=" AND A.MB_CD = C.MB_CD  ";
        query +=" AND B.SN_MNG_NUM(+) = '03'  ";
        query +=" AND A.MB_GBN2 = 2  ";
        query +=" AND C.TRNSF_GBN = 1  ";
        query +=" AND A.MB_GBN1 = 'W'  ";
        query +=" AND A.NATY_CD = '082'  ";
        query +=" AND A.DEAD_DAY IS NULL  ";
        query +=" AND SUBSTR(C.SUPP_YRMN,1,4) BETWEEN TO_NUMBER(:YEAR)-3  ";
        query +=" AND TO_NUMBER(:YEAR)-1  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.MB_CD = :MB_CD --||'%'  ";
        }
        query +=" AND A.SECT_CD LIKE :SECT_CD||'%'  ";
        query +=" AND A.MB_CD NOT IN (SELECT X.MB_CD  ";
        query +=" FROM FIDU.TMEM_MB X  ";
        query +=" WHERE TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN NVL( X.QAFCSTOP_START_DAY , '29991231' )  ";
        query +=" AND NVL( X.QAFCSTOP_END_DAY , '29991231'))  ";
        query +=" AND A.MB_CD IN (  ";
        query +=" SELECT Y.MB_CD  ";
        query +=" FROM FIDU.TMEM_TRUSTTRM Y  ";
        query +=" WHERE TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN NVL( Y.TRUSTTRM_START_DAY , '29991231' )  ";
        query +=" AND NVL( Y.TRUSTTRM_END_DAY , '29991231'))  ";
        query +=" GROUP BY A.MB_CD )  ";
        query +=" WHERE TOT_ORGDISTR_AMT <> 0  ";
        query +=" ORDER BY TOT_ORGDISTR_AMT DESC  ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //�˻��⵵
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        }
        sobj.setString("SECT_CD", SECT_CD);               //�ι� �ڵ�
        return sobj;
    }
    //##**$$searchMst
    //##**$$end
}
