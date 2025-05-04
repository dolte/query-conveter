
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s11
{
    public bra04_s11()
    {
    }
    //##**$$bra04_search
    /* * ���α׷��� : bra04_s11
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/26
    * ����    : �ش� ������ ���۳��, ������ ���̿� ��ϵ� ä�ǳ��� ������ ��ȸ�Ѵ�.
    Input
    BRAN_CD (���� �ڵ�)
    END_YRMN (������)
    START_YRMN (���۳��)
    * ������ : 2010.03.04
    * ������ :
    * �������� : ������� �÷� (INS_DAY)�߰�/ ������ڼ� ���� �߰�
    */
    public DOBJ CTLbra04_search(DOBJ dobj)
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
            dobj  = CALLbra04_search_SEL1(Conn, dobj);           //  ����ä������
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
    public DOBJ CTLbra04_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra04_search_SEL1(Conn, dobj);           //  ����ä������
        return dobj;
    }
    // ����ä������
    // 2018.04.13 �̴ټ� �߰� ȣ�����ο�û�� ����(������ �۾���û�� ����) ����� ���� �߰�
    public DOBJ CALLbra04_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra04_search_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  B.APPTN_YRMN  ,  A.BRAN_CD  ,  GIBU.GET_BRAN_NM(A.BRAN_CD)  AS  BRAN_NM  ,  A.UPSO_NM  ,  A.BIOWN_NUM  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  UPSO_ADDR  ,  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO  MNGEMSTR_ADDR  ,  A.MNGEMSTR_NM  ,  A.STAFF_CD  ,  A.UPSO_PHON  ,  A.MNGEMSTR_PHONNUM  ,  A.MNGEMSTR_HPNUM  ,  A.MNGEMSTR_RESINUM  ,  B.MONPRNCFEE  ,  B.DEMD_MMCNT  ,  B.START_YRMN  ,  B.END_YRMN  ,  B.TOT_DEMD_AMT  ,  B.TOT_USE_AMT  ,  B.TOT_ADDT_AMT  ,  C.GRADNM  ,  B.SOL_START_YRMN  ,  B.SOL_END_YRMN  ,  B.SOL_AMT  ,  B.COMIS  ,  B.DIFF_AMT  ,  B.COMPN_DAY  ,  B.REPT_DAY  ,  B.REPT_NUM  ,  B.REPT_GBN  ,  TO_CHAR(B.INS_DATE,  'YYYYMMDD')  INS_DAY  ,  FIDU.GET_STAFF_NM(A.STAFF_CD)  AS  STAFF_NM  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_DLGTN_CLAIM  B  ,  (   \n";
        query +=" SELECT  BC.GRADNM  ,  ZA.UPSO_CD  FROM  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  APPTN_YRMN  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN  )  XB  WHERE  XA.UPSO_CD  =  XB.UPSO_CD  )  ZA  GROUP  BY  ZA.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_BSTYPGRAD  BC  WHERE  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.BSTYP_CD  =  ZC.BSTYP_CD   \n";
        query +=" AND  ZB.UPSO_GRAD  =  ZC.GRAD_GBN   \n";
        query +=" AND  BC.BSTYP_CD  ='z'   \n";
        query +=" AND  BC.GRAD_GBN  =  ZC.BSTYP_CD  )  C  WHERE  B.APPTN_YRMN  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  ORDER  BY  A.BRAN_CD,  B.INS_DATE,  A.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$bra04_search
    //##**$$end
}
