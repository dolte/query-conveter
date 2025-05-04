
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_r04
{
    public bra04_r04()
    {
    }
    //##**$$sending_info
    /* * 프로그램명 : bra04_r04
    * 작성자 : 서정재
    * 작성일 : 2009/09/16
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsending_info(DOBJ dobj)
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
            dobj  = CALLsending_info_SEL8(Conn, dobj);           //  날짜변환
            if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") < 2019)
            {
                dobj  = CALLsending_info_SEL4(Conn, dobj);           //  발송물출력현황
                dobj  = CALLsending_info_MRG9( dobj);        //  머
            }
            else if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") >= 2019 && dobj.getRetObject("SEL8").getRecord().getDouble("CNT") <= 2020)
            {
                dobj  = CALLsending_info_SEL7(Conn, dobj);           //  발송물출력현황
                dobj  = CALLsending_info_MRG9( dobj);        //  머
            }
            else if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") >= 2021)
            {
                dobj  = CALLsending_info_SEL10(Conn, dobj);           //  발송물출력현황
                dobj  = CALLsending_info_MRG9( dobj);        //  머
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
    public DOBJ CTLsending_info( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsending_info_SEL8(Conn, dobj);           //  날짜변환
        if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") < 2019)
        {
            dobj  = CALLsending_info_SEL4(Conn, dobj);           //  발송물출력현황
            dobj  = CALLsending_info_MRG9( dobj);        //  머
        }
        else if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") >= 2019 && dobj.getRetObject("SEL8").getRecord().getDouble("CNT") <= 2020)
        {
            dobj  = CALLsending_info_SEL7(Conn, dobj);           //  발송물출력현황
            dobj  = CALLsending_info_MRG9( dobj);        //  머
        }
        else if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") >= 2021)
        {
            dobj  = CALLsending_info_SEL10(Conn, dobj);           //  발송물출력현황
            dobj  = CALLsending_info_MRG9( dobj);        //  머
        }
        return dobj;
    }
    // 날짜변환
    public DOBJ CALLsending_info_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsending_info_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsending_info_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //검색년도
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_NUMBER(:YEAR)  AS  CNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //검색년도
        return sobj;
    }
    // 발송물출력현황
    public DOBJ CALLsending_info_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsending_info_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsending_info_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //검색년도
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.YYYYMM  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  RNUM  ,  MAX(A_DAY)  A_DAY  ,  MAX(B_DAY)  B_DAY  ,  MAX(C_DAY)  C_DAY  ,  MAX(D_DAY)  D_DAY  ,  MAX(E_DAY)  E_DAY  ,  MAX(F_DAY)  F_DAY  ,  MAX(G_DAY)  G_DAY  ,  MAX(H_DAY)  H_DAY  ,  MAX(I_DAY)  I_DAY  ,  MAX(J_DAY)  J_DAY  ,  MAX(K_DAY)  K_DAY  FROM(   \n";
        query +=" SELECT  A_YRMN  ,  RNUM  ,  DECODE(A_DAY,  NULL,  '',  SUBSTR(A_DAY,1,4)||'/'||SUBSTR(A_DAY,5,2)||'/'||SUBSTR(A_DAY,7,2))  A_DAY  ,  DECODE(B_DAY,  NULL,  '',  SUBSTR(B_DAY,1,4)||'/'||SUBSTR(B_DAY,5,2)||'/'||SUBSTR(B_DAY,7,2))  B_DAY  ,  DECODE(C_DAY,  NULL,  '',  SUBSTR(C_DAY,1,4)||'/'||SUBSTR(C_DAY,5,2)||'/'||SUBSTR(C_DAY,7,2))  C_DAY  ,  DECODE(D_DAY,  NULL,  '',  SUBSTR(D_DAY,1,4)||'/'||SUBSTR(D_DAY,5,2)||'/'||SUBSTR(D_DAY,7,2))  D_DAY  ,  DECODE(E_DAY,  NULL,  '',  SUBSTR(E_DAY,1,4)||'/'||SUBSTR(E_DAY,5,2)||'/'||SUBSTR(E_DAY,7,2))  E_DAY  ,  DECODE(F_DAY,  NULL,  '',  SUBSTR(F_DAY,1,4)||'/'||SUBSTR(F_DAY,5,2)||'/'||SUBSTR(F_DAY,7,2))  F_DAY  ,  DECODE(G_DAY,  NULL,  '',  SUBSTR(G_DAY,1,4)||'/'||SUBSTR(G_DAY,5,2)||'/'||SUBSTR(G_DAY,7,2))  G_DAY  ,  DECODE(H_DAY,  NULL,  '',  SUBSTR(H_DAY,1,4)||'/'||SUBSTR(H_DAY,5,2)||'/'||SUBSTR(H_DAY,7,2))  H_DAY  ,  DECODE(I_DAY,  NULL,  '',  SUBSTR(I_DAY,1,4)||'/'||SUBSTR(I_DAY,5,2)||'/'||SUBSTR(I_DAY,7,2))  I_DAY  ,  DECODE(J_DAY,  NULL,  '',  SUBSTR(J_DAY,1,4)||'/'||SUBSTR(J_DAY,5,2)||'/'||SUBSTR(J_DAY,7,2))  J_DAY  ,  DECODE(K_DAY,  NULL,  '',  SUBSTR(K_DAY,1,4)||'/'||SUBSTR(K_DAY,5,2)||'/'||SUBSTR(K_DAY,7,2))  K_DAY  FROM  (   \n";
        query +=" SELECT  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.INS_DATE,'YYYYMMDD')  A_DAY  ,  A.DEMD_YRMN  A_YYYYMM  ,  row_number()  over  (partition  by  A.DEMD_YRMN  order  by  TO_CHAR(A.INS_DATE,'YYYYMMDD')  )  as  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  LIKE  :YEAR  ||  '%'   \n";
        query +=" AND  A.OCR_PRNT  =  '1'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.A_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.INS_DATE,'YYYYMMDD')  B_DAY  ,  A.DEMD_YRMN  B_YYYYMM  ,  row_number()  over  (partition  by  A.DEMD_YRMN  order  by  TO_CHAR(A.INS_DATE,'YYYYMMDD')  )  as  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  LIKE  :YEAR  ||  '%'   \n";
        query +=" AND  A.OCR_PRNT  =  '2'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.B_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  C_DAY  ,  SUBSTR(PRNT_DAY,1,6)  C_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(PRNT_DAY,1,6)  order  by  PRNT_DAY  )  as  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'O'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  :YEAR||'%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.  C_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  D_DAY  ,  SUBSTR(PRNT_DAY,1,6)  D_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(PRNT_DAY,1,6)  order  by  PRNT_DAY  )  as  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'M'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  :YEAR||'%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  BPAP_DAY  E_DAY  ,  SUBSTR(BPAP_DAY,1,6)  E_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(BPAP_DAY,1,6)  order  by  BPAP_DAY  )  as  RNUM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_GBN  =  '2'   \n";
        query +=" AND  A.BPAP_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.E_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  BPAP_DAY  F_DAY  ,  SUBSTR(BPAP_DAY,1,6)  F_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(BPAP_DAY,1,6)  order  by  BPAP_DAY  )  as  RNUM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_GBN  =  '3'   \n";
        query +=" AND  A.BPAP_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.F_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  G_DAY  ,  SUBSTR(DISP_DAY,1,6)  G_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(DISP_DAY,1,6)  order  by  DISP_DAY  )  as  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  :YEAR  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'A'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.G_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  H_DAY  ,  SUBSTR(DISP_DAY,1,6)  H_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(DISP_DAY,1,6)  order  by  DISP_DAY  )  as  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  :YEAR  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'B'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.H_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  I_DAY  ,  SUBSTR(DISP_DAY,1,6)  I_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(DISP_DAY,1,6)  order  by  DISP_DAY  )  as  RNUM  FROM  GIBU.TBRA_CONTR_TERM  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.I_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  VISIT_DAY  J_DAY  ,  SUBSTR(VISIT_DAY,1,6)  D_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(VISIT_DAY,1,6)  order  by  VISIT_DAY  )  as  RNUM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.JOB_GBN  =  'L'   \n";
        query +=" AND  A.VISIT_DAY  LIKE  :YEAR||'%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.SEND_DATE1,  'YYYYMMDD')  K_DAY  ,  TO_CHAR(A.SEND_DATE1,  'YYYYMM')  D_YYYYMM  ,  row_number()  over  (partition  by  TO_CHAR(A.SEND_DATE1,  'YYYYMM')  order  by  A.SEND_DATE1  )  as  RNUM  FROM  GIBU.TBRA_DEMD_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.PROC_GBN  =  '1'   \n";
        query +=" AND  A.DEMD_YRMN  LIKE  :YEAR||'%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  )  XA  WHERE  (A_DAY  IS  NOT  NULL   \n";
        query +=" OR  B_DAY  IS  NOT  NULL   \n";
        query +=" OR  C_DAY  IS  NOT  NULL   \n";
        query +=" OR  D_DAY  IS  NOT  NULL   \n";
        query +=" OR  E_DAY  IS  NOT  NULL   \n";
        query +=" OR  F_DAY  IS  NOT  NULL   \n";
        query +=" OR  G_DAY  IS  NOT  NULL   \n";
        query +=" OR  H_DAY  IS  NOT  NULL   \n";
        query +=" OR  I_DAY  IS  NOT  NULL   \n";
        query +=" OR  J_DAY  IS  NOT  NULL   \n";
        query +=" OR  K_DAY  IS  NOT  NULL)  )  A,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  :YEAR||  A.A_YRMN(+)  =  B.YYYYMM  GROUP  BY  B.YYYYMM,A.RNUM  ORDER  BY  B.YYYYMM,A.RNUM ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 머
    public DOBJ CALLsending_info_MRG9(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG9");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL4, SEL7, SEL10","");
        rvobj.setName("MRG9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 발송물출력현황
    public DOBJ CALLsending_info_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsending_info_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsending_info_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //검색년도
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.YYYYMM  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  RNUM  ,  MAX(A_DAY)  AS  A_DAY  ,  MAX(B_DAY)  AS  B_DAY  ,  MAX(C_DAY)  AS  C_DAY  ,  MAX(D_DAY)  AS  D_DAY  ,  MAX(E_DAY)  AS  E_DAY  ,  MAX(F_DAY)  AS  F_DAY  ,  MAX(G_DAY)  AS  G_DAY  ,  MAX(H_DAY)  AS  H_DAY  ,  MAX(I_DAY)  AS  I_DAY  ,  MAX(J_DAY)  AS  J_DAY  ,  MAX(K_DAY)  AS  K_DAY  FROM  (   \n";
        query +=" SELECT  A_YRMN  ,  RNUM  ,  DECODE(A_DAY,  NULL,  '',  SUBSTR(A_DAY,  1,  4)  ||  '/'  ||  SUBSTR(A_DAY,  5,  2)  ||  '/'  ||  SUBSTR(A_DAY,  7,  2))  AS  A_DAY  ,  DECODE(B_DAY,  NULL,  '',  SUBSTR(B_DAY,  1,  4)  ||  '/'  ||  SUBSTR(B_DAY,  5,  2)  ||  '/'  ||  SUBSTR(B_DAY,  7,  2))  AS  B_DAY  ,  DECODE(C_DAY,  NULL,  '',  SUBSTR(C_DAY,  1,  4)  ||  '/'  ||  SUBSTR(C_DAY,  5,  2)  ||  '/'  ||  SUBSTR(C_DAY,  7,  2))  AS  C_DAY  ,  DECODE(D_DAY,  NULL,  '',  SUBSTR(D_DAY,  1,  4)  ||  '/'  ||  SUBSTR(D_DAY,  5,  2)  ||  '/'  ||  SUBSTR(D_DAY,  7,  2))  AS  D_DAY  ,  DECODE(E_DAY,  NULL,  '',  SUBSTR(E_DAY,  1,  4)  ||  '/'  ||  SUBSTR(E_DAY,  5,  2)  ||  '/'  ||  SUBSTR(E_DAY,  7,  2))  AS  E_DAY  ,  DECODE(F_DAY,  NULL,  '',  SUBSTR(F_DAY,  1,  4)  ||  '/'  ||  SUBSTR(F_DAY,  5,  2)  ||  '/'  ||  SUBSTR(F_DAY,  7,  2))  AS  F_DAY  ,  DECODE(G_DAY,  NULL,  '',  SUBSTR(G_DAY,  1,  4)  ||  '/'  ||  SUBSTR(G_DAY,  5,  2)  ||  '/'  ||  SUBSTR(G_DAY,  7,  2))  AS  G_DAY  ,  DECODE(H_DAY,  NULL,  '',  SUBSTR(H_DAY,  1,  4)  ||  '/'  ||  SUBSTR(H_DAY,  5,  2)  ||  '/'  ||  SUBSTR(H_DAY,  7,  2))  AS  H_DAY  ,  DECODE(I_DAY,  NULL,  '',  SUBSTR(I_DAY,  1,  4)  ||  '/'  ||  SUBSTR(I_DAY,  5,  2)  ||  '/'  ||  SUBSTR(I_DAY,  7,  2))  AS  I_DAY  ,  DECODE(J_DAY,  NULL,  '',  SUBSTR(J_DAY,  1,  4)  ||  '/'  ||  SUBSTR(J_DAY,  5,  2)  ||  '/'  ||  SUBSTR(J_DAY,  7,  2))  AS  J_DAY  ,  DECODE(K_DAY,  NULL,  '',  SUBSTR(K_DAY,  1,  4)  ||  '/'  ||  SUBSTR(K_DAY,  5,  2)  ||  '/'  ||  SUBSTR(K_DAY,  7,  2))  AS  K_DAY  FROM  (   \n";
        query +=" SELECT  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  A_DAY  ,  A.DEMD_YRMN  AS  A_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  A.DEMD_YRMN  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  BETWEEN  :YEAR  ||  '0101'   \n";
        query +=" AND  :YEAR  ||  '1231'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_10'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.A_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  B_DAY  ,  A.DEMD_YRMN  AS  B_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  A.DEMD_YRMN  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  BETWEEN  :YEAR  ||  '0101'   \n";
        query +=" AND  :YEAR  ||  '1231'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_11'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.B_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  AS  C_DAY  ,  SUBSTR(PRNT_DAY,  1,  6)  AS  C_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(PRNT_DAY,  1,  6)  ORDER  BY  PRNT_DAY)  AS  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'O'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.C_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  AS  D_DAY  ,  SUBSTR  (PRNT_DAY,  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(PRNT_DAY,  1,  6)  ORDER  BY  PRNT_DAY)  AS  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'M'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  E_DAY  ,  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  AS  E_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  C.REQ_DTM  BETWEEN  :YEAR  ||  '0101'   \n";
        query +=" AND  :YEAR  ||  '1231'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_09'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.E_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  BPAP_DAY  AS  F_DAY  ,  SUBSTR(BPAP_DAY,  1,  6)  AS  F_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(BPAP_DAY,  1,  6)  ORDER  BY  BPAP_DAY)  AS  RNUM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_GBN  =  '3'   \n";
        query +=" AND  A.BPAP_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.F_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  ROWNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  G_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  G_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  :YEAR  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'A'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.G_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  H_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  H_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  :YEAR  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'B'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.H_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  I_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  I_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_CONTR_TERM  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.I_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  VISIT_DAY  AS  J_DAY  ,  SUBSTR(VISIT_DAY,  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(VISIT_DAY,  1,  6)  ORDER  BY  VISIT_DAY)  AS  RNUM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.JOB_GBN  =  'L'   \n";
        query +=" AND  A.VISIT_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  K_DAY  ,  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_AUTO  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.PROC_GBN  =  '1'   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  BETWEEN  :YEAR  ||  '0101'   \n";
        query +=" AND  :YEAR  ||  '1231'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_12'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  )  XA  WHERE  (  A_DAY  IS  NOT  NULL   \n";
        query +=" OR  B_DAY  IS  NOT  NULL   \n";
        query +=" OR  C_DAY  IS  NOT  NULL   \n";
        query +=" OR  D_DAY  IS  NOT  NULL   \n";
        query +=" OR  E_DAY  IS  NOT  NULL   \n";
        query +=" OR  F_DAY  IS  NOT  NULL   \n";
        query +=" OR  G_DAY  IS  NOT  NULL   \n";
        query +=" OR  H_DAY  IS  NOT  NULL   \n";
        query +=" OR  I_DAY  IS  NOT  NULL   \n";
        query +=" OR  J_DAY  IS  NOT  NULL   \n";
        query +=" OR  K_DAY  IS  NOT  NULL)  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  :YEAR  ||  A.A_YRMN(+)  =  B.YYYYMM  GROUP  BY  B.YYYYMM,  A.RNUM  ORDER  BY  B.YYYYMM,  A.RNUM ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 발송물출력현황
    public DOBJ CALLsending_info_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsending_info_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsending_info_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //검색년도
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A_YRMN  ,  DECODE(A_DAY,  NULL,  '',  SUBSTR(A_DAY,1,4)||'/'||SUBSTR(A_DAY,5,2)||'/'||SUBSTR(A_DAY,7,2))  A_DAY  ,  B_YRMN  ,  DECODE(B_DAY,  NULL,  '',  SUBSTR(B_DAY,1,4)||'/'||SUBSTR(B_DAY,5,2)||'/'||SUBSTR(B_DAY,7,2))  B_DAY  ,  C_YRMN  ,  DECODE(C_DAY,  NULL,  '',  SUBSTR(C_DAY,1,4)||'/'||SUBSTR(C_DAY,5,2)||'/'||SUBSTR(C_DAY,7,2))  C_DAY  ,  D_YRMN  ,  DECODE(D_DAY,  NULL,  '',  SUBSTR(D_DAY,1,4)||'/'||SUBSTR(D_DAY,5,2)||'/'||SUBSTR(D_DAY,7,2))  D_DAY  ,  E_YRMN  ,  DECODE(E_DAY,  NULL,  '',  SUBSTR(E_DAY,1,4)||'/'||SUBSTR(E_DAY,5,2)||'/'||SUBSTR(E_DAY,7,2))  E_DAY  ,  F_YRMN  ,  DECODE(F_DAY,  NULL,  '',  SUBSTR(F_DAY,1,4)||'/'||SUBSTR(F_DAY,5,2)||'/'||SUBSTR(F_DAY,7,2))  F_DAY  ,  G_YRMN  ,  DECODE(G_DAY,  NULL,  '',  SUBSTR(G_DAY,1,4)||'/'||SUBSTR(G_DAY,5,2)||'/'||SUBSTR(G_DAY,7,2))  G_DAY  ,  H_YRMN  ,  DECODE(H_DAY,  NULL,  '',  SUBSTR(H_DAY,1,4)||'/'||SUBSTR(H_DAY,5,2)||'/'||SUBSTR(H_DAY,7,2))  H_DAY  ,  I_YRMN  ,  DECODE(I_DAY,  NULL,  '',  SUBSTR(I_DAY,1,4)||'/'||SUBSTR(I_DAY,5,2)||'/'||SUBSTR(I_DAY,7,2))  I_DAY  FROM  (   \n";
        query +=" SELECT  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.INS_DATE,'YYYYMMDD')  A_DAY  ,  A.DEMD_YRMN  A_YYYYMM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  LIKE  :YEAR  ||  '%'   \n";
        query +=" AND  A.OCR_PRNT  =  '1'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.A_YYYYMM(+)  =  B.YYYYMM  )  XA  ,  (   \n";
        query +=" SELECT  B_DAY  ,  SUBSTR(B.YYYYMM,5,2)  B_YRMN  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.INS_DATE,'YYYYMMDD')  B_DAY  ,  A.DEMD_YRMN  B_YYYYMM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  LIKE  :YEAR  ||  '%'   \n";
        query +=" AND  A.OCR_PRNT  =  '2'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.B_YYYYMM(+)  =  B.YYYYMM  )  XB  ,  (   \n";
        query +=" SELECT  C_DAY  ,  SUBSTR(B.YYYYMM,5,2)  C_YRMN  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  C_DAY  ,  SUBSTR(PRNT_DAY,1,6)  C_YYYYMM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'O'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  :YEAR||'%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.  C_YYYYMM(+)  =  B.YYYYMM  )  XC  ,  (   \n";
        query +=" SELECT  D_DAY  ,  SUBSTR(B.YYYYMM,5,2)  D_YRMN  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  D_DAY  ,  SUBSTR(PRNT_DAY,1,6)  D_YYYYMM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'M'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  :YEAR||'%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM(+)  =  B.YYYYMM  )  XD  ,  (   \n";
        query +=" SELECT  E_DAY  ,  SUBSTR(B.YYYYMM,5,2)  E_YRMN  FROM  (   \n";
        query +=" SELECT  BPAP_DAY  E_DAY  ,  SUBSTR(BPAP_DAY,1,6)  E_YYYYMM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_GBN  =  '2'   \n";
        query +=" AND  A.BPAP_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.E_YYYYMM(+)  =  B.YYYYMM  )  XE  ,  (   \n";
        query +=" SELECT  F_DAY  ,  SUBSTR(B.YYYYMM,5,2)  F_YRMN  FROM  (   \n";
        query +=" SELECT  BPAP_DAY  F_DAY  ,  SUBSTR(BPAP_DAY,1,6)  F_YYYYMM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_GBN  =  '3'   \n";
        query +=" AND  A.BPAP_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.F_YYYYMM(+)  =  B.YYYYMM  )  XF  ,  (   \n";
        query +=" SELECT  G_DAY  ,  SUBSTR(B.YYYYMM,5,2)  G_YRMN  FROM  (   \n";
        query +=" SELECT  DISP_DAY  G_DAY  ,  SUBSTR(DISP_DAY,1,6)  G_YYYYMM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  :YEAR  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'A'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.G_YYYYMM(+)  =  B.YYYYMM  )  XG  ,  (   \n";
        query +=" SELECT  H_DAY  ,  SUBSTR(B.YYYYMM,5,2)  H_YRMN  FROM  (   \n";
        query +=" SELECT  DISP_DAY  H_DAY  ,  SUBSTR(DISP_DAY,1,6)  H_YYYYMM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  :YEAR  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'B'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.H_YYYYMM(+)  =  B.YYYYMM  )  XH  ,  (   \n";
        query +=" SELECT  I_DAY  ,  SUBSTR(B.YYYYMM,5,2)  I_YRMN  FROM  (   \n";
        query +=" SELECT  DISP_DAY  I_DAY  ,  SUBSTR(DISP_DAY,1,6)  I_YYYYMM  FROM  GIBU.TBRA_CONTR_TERM  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.I_YYYYMM(+)  =  B.YYYYMM  )  XI  WHERE  XA.A_YRMN  =  XB.B_YRMN   \n";
        query +=" AND  XB.B_YRMN  =  XC.C_YRMN   \n";
        query +=" AND  XC.C_YRMN  =  XD.D_YRMN   \n";
        query +=" AND  XD.D_YRMN  =  XE.E_YRMN   \n";
        query +=" AND  XE.E_YRMN  =  XF.F_YRMN   \n";
        query +=" AND  XF.F_YRMN  =  XG.G_YRMN   \n";
        query +=" AND  XG.G_YRMN  =  XH.H_YRMN   \n";
        query +=" AND  XH.H_YRMN  =  XI.I_YRMN  ORDER  BY  XA.A_YRMN ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 발송물출력현황
    public DOBJ CALLsending_info_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsending_info_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsending_info_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //검색년도
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.YYYYMM  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  RNUM  ,  MAX(A_DAY)  AS  A_DAY  ,  MAX(B_DAY)  AS  B_DAY  ,  MAX(C_DAY)  AS  C_DAY  ,  MAX(D_DAY)  AS  D_DAY  ,  MAX(E_DAY)  AS  E_DAY  ,  MAX(F_DAY)  AS  F_DAY  ,  MAX(G_DAY)  AS  G_DAY  ,  MAX(H_DAY)  AS  H_DAY  ,  MAX(I_DAY)  AS  I_DAY  ,  MAX(J_DAY)  AS  J_DAY  ,  MAX(K_DAY)  AS  K_DAY  FROM  (   \n";
        query +=" SELECT  A_YRMN  ,  RNUM  ,  DECODE(A_DAY,  NULL,  '',  SUBSTR(A_DAY,  1,  4)  ||  '/'  ||  SUBSTR(A_DAY,  5,  2)  ||  '/'  ||  SUBSTR(A_DAY,  7,  2))  AS  A_DAY  ,  DECODE(B_DAY,  NULL,  '',  SUBSTR(B_DAY,  1,  4)  ||  '/'  ||  SUBSTR(B_DAY,  5,  2)  ||  '/'  ||  SUBSTR(B_DAY,  7,  2))  AS  B_DAY  ,  DECODE(C_DAY,  NULL,  '',  SUBSTR(C_DAY,  1,  4)  ||  '/'  ||  SUBSTR(C_DAY,  5,  2)  ||  '/'  ||  SUBSTR(C_DAY,  7,  2))  AS  C_DAY  ,  DECODE(D_DAY,  NULL,  '',  SUBSTR(D_DAY,  1,  4)  ||  '/'  ||  SUBSTR(D_DAY,  5,  2)  ||  '/'  ||  SUBSTR(D_DAY,  7,  2))  AS  D_DAY  ,  DECODE(E_DAY,  NULL,  '',  SUBSTR(E_DAY,  1,  4)  ||  '/'  ||  SUBSTR(E_DAY,  5,  2)  ||  '/'  ||  SUBSTR(E_DAY,  7,  2))  AS  E_DAY  ,  DECODE(F_DAY,  NULL,  '',  SUBSTR(F_DAY,  1,  4)  ||  '/'  ||  SUBSTR(F_DAY,  5,  2)  ||  '/'  ||  SUBSTR(F_DAY,  7,  2))  AS  F_DAY  ,  DECODE(G_DAY,  NULL,  '',  SUBSTR(G_DAY,  1,  4)  ||  '/'  ||  SUBSTR(G_DAY,  5,  2)  ||  '/'  ||  SUBSTR(G_DAY,  7,  2))  AS  G_DAY  ,  DECODE(H_DAY,  NULL,  '',  SUBSTR(H_DAY,  1,  4)  ||  '/'  ||  SUBSTR(H_DAY,  5,  2)  ||  '/'  ||  SUBSTR(H_DAY,  7,  2))  AS  H_DAY  ,  DECODE(I_DAY,  NULL,  '',  SUBSTR(I_DAY,  1,  4)  ||  '/'  ||  SUBSTR(I_DAY,  5,  2)  ||  '/'  ||  SUBSTR(I_DAY,  7,  2))  AS  I_DAY  ,  DECODE(J_DAY,  NULL,  '',  SUBSTR(J_DAY,  1,  4)  ||  '/'  ||  SUBSTR(J_DAY,  5,  2)  ||  '/'  ||  SUBSTR(J_DAY,  7,  2))  AS  J_DAY  ,  DECODE(K_DAY,  NULL,  '',  SUBSTR(K_DAY,  1,  4)  ||  '/'  ||  SUBSTR(K_DAY,  5,  2)  ||  '/'  ||  SUBSTR(K_DAY,  7,  2))  AS  K_DAY  FROM  (   \n";
        query +=" SELECT  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  A_DAY  ,  A.DEMD_YRMN  AS  A_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  A.DEMD_YRMN  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_16'   \n";
        query +=" AND  C.RESERVED4  IS  NULL  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.A_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  B_DAY  ,  A.DEMD_YRMN  AS  B_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  A.DEMD_YRMN  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_16'   \n";
        query +=" AND  C.RESERVED4  =  'DMND'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.B_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  AS  C_DAY  ,  SUBSTR(PRNT_DAY,  1,  6)  AS  C_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(PRNT_DAY,  1,  6)  ORDER  BY  PRNT_DAY)  AS  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'O'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.C_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  AS  D_DAY  ,  SUBSTR  (PRNT_DAY,  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(PRNT_DAY,  1,  6)  ORDER  BY  PRNT_DAY)  AS  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'M'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  E_DAY  ,  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  AS  E_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  C.REQ_DTM  BETWEEN  :YEAR  ||  '0101'   \n";
        query +=" AND  :YEAR  ||  '1231'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_09'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.E_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  VISIT_DAY  AS  F_DAY  ,  SUBSTR(VISIT_DAY,  1,  6)  AS  F_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(VISIT_DAY,  1,  6)  ORDER  BY  VISIT_DAY)  AS  RNUM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.JOB_GBN  =  'C'   \n";
        query +=" AND  A.VISIT_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.F_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  ROWNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  G_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  G_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  :YEAR  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'A'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.G_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  H_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  H_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  :YEAR  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'B'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.H_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  I_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  I_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_CONTR_TERM  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.I_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  VISIT_DAY  AS  J_DAY  ,  SUBSTR(VISIT_DAY,  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(VISIT_DAY,  1,  6)  ORDER  BY  VISIT_DAY)  AS  RNUM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.JOB_GBN  =  'L'   \n";
        query +=" AND  A.VISIT_DAY  LIKE  :YEAR  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  K_DAY  ,  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_AUTO  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.PROC_GBN  =  '1'   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_17'  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  )  XA  WHERE  (  A_DAY  IS  NOT  NULL   \n";
        query +=" OR  B_DAY  IS  NOT  NULL   \n";
        query +=" OR  C_DAY  IS  NOT  NULL   \n";
        query +=" OR  D_DAY  IS  NOT  NULL   \n";
        query +=" OR  E_DAY  IS  NOT  NULL   \n";
        query +=" OR  F_DAY  IS  NOT  NULL   \n";
        query +=" OR  G_DAY  IS  NOT  NULL   \n";
        query +=" OR  H_DAY  IS  NOT  NULL   \n";
        query +=" OR  I_DAY  IS  NOT  NULL   \n";
        query +=" OR  J_DAY  IS  NOT  NULL   \n";
        query +=" OR  K_DAY  IS  NOT  NULL)  )  A  ,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  :YEAR  ||  A.A_YRMN(+)  =  B.YYYYMM  GROUP  BY  B.YYYYMM,  A.RNUM  ORDER  BY  B.YYYYMM,  A.RNUM ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$sending_info
    //##**$$balance_detail
    /* * 프로그램명 : bra04_r04
    * 작성자 : 서정재
    * 작성일 : 2009/12/15
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbalance_detail(DOBJ dobj)
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
            dobj  = CALLbalance_detail_SEL1(Conn, dobj);           //  상세조회
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
    public DOBJ CTLbalance_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbalance_detail_SEL1(Conn, dobj);           //  상세조회
        return dobj;
    }
    // 상세조회
    // 더넣은금액 상세조회
    public DOBJ CALLbalance_detail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbalance_detail_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbalance_detail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  D.CODE_NM  REPT_GBN  ,  A.REPT_AMT  ,  A.PROC_AMT  ,  A.BALANCE  ,  DECODE(B.MAPPING_DAY,  NULL,  C.MAPPING_DAY,  B.MAPPING_DAY)  MAPPING_DAY  FROM  GIBU.TBRA_REPT_BALANCE  A  ,  GIBU.TBRA_REPT  B  ,  GIBU.TBRA_REPT_UPSO_DISTR  C  ,  FIDU.TENV_CODE  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.REPT_DAY(+)  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM(+)  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN(+)  =  A.REPT_GBN   \n";
        query +=" AND  B.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  C.REPT_DAY(+)  =  A.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM(+)  =  A.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN(+)  =  A.REPT_GBN   \n";
        query +=" AND  C.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  D.HIGH_CD  =  '00141'   \n";
        query +=" AND  D.CODE_CD  =  A.REPT_GBN  ORDER  BY  A.PROC_DAY  ASC,  A.PROC_NUM  ASC ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$balance_detail
    //##**$$minab_goso_info
    /* * 프로그램명 : bra04_r04
    * 작성자 : 서정재
    * 작성일 : 2009/11/23
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLminab_goso_info(DOBJ dobj)
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
            ////
            double   CNT = 0;         //카운트
            dobj.setGVValue("CNT",CNT+"");
            dobj  = CALLminab_goso_info_SEL3(Conn, dobj);           //  청구조회조건
            dobj  = CALLminab_goso_info_OSP1(Conn, dobj);           //  청구 금액 생성
            dobj  = CALLminab_goso_info_SEL11(Conn, dobj);           //  미납개월수구하기
            dobj  = CALLminab_goso_info_SEL5(Conn, dobj);           //  비교월정보
            dobj  = CALLminab_goso_info_MPD6(Conn, dobj);           //  건별처리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLminab_goso_info_SEL1(Conn, dobj);           //  최종 미징수(미납)정보
            dobj  = CALLminab_goso_info_SEL2(Conn, dobj);           //  최종고소정보
            dobj  = CALLminab_goso_info_SEL14(Conn, dobj);           //  더넣은금액조회
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
    public DOBJ CTLminab_goso_info( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        ////
        double   CNT = 0;         //카운트
        dobj.setGVValue("CNT",CNT+"");
        dobj  = CALLminab_goso_info_SEL3(Conn, dobj);           //  청구조회조건
        dobj  = CALLminab_goso_info_OSP1(Conn, dobj);           //  청구 금액 생성
        dobj  = CALLminab_goso_info_SEL11(Conn, dobj);           //  미납개월수구하기
        dobj  = CALLminab_goso_info_SEL5(Conn, dobj);           //  비교월정보
        dobj  = CALLminab_goso_info_MPD6(Conn, dobj);           //  건별처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLminab_goso_info_SEL1(Conn, dobj);           //  최종 미징수(미납)정보
        dobj  = CALLminab_goso_info_SEL2(Conn, dobj);           //  최종고소정보
        dobj  = CALLminab_goso_info_SEL14(Conn, dobj);           //  더넣은금액조회
        return dobj;
    }
    // 청구조회조건
    public DOBJ CALLminab_goso_info_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLminab_goso_info_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLminab_goso_info_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   RECV_DAY ="";   //영수 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,6)  LAST_YRMN  ,  UPSO_CD  ,  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,6)  START_YRMN  ,  DECODE(CLSBS_YRMN,  NULL,  TO_CHAR(SYSDATE,'YYYYMM'),  CLSBS_YRMN)  END_YRMN  ,  :RECV_DAY  AS  RECV_DAY  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("RECV_DAY", RECV_DAY);               //영수 일자
        return sobj;
    }
    // 청구 금액 생성
    // 해당 업소의 청구 금액를 생성하기 위한 프로시저 (GIBU.SP_GET_USE_AMT) 를 호출한다
    public DOBJ CALLminab_goso_info_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL3");         //청구조회조건에서 생성시킨 OBJECT입니다.(CALLminab_goso_info_SEL3)
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","CRET_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRET_GBN ="O";         //청구 년월
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
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 미납개월수구하기
    public DOBJ CALLminab_goso_info_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLminab_goso_info_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLminab_goso_info_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("SEL3").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("SEL3").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MONTHS_BETWEEN(TO_DATE(:END_YRMN,  'YYYYMM'),  TO_DATE(:START_YRMN,  'YYYYMM'))  +  1  MON_CNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 비교월정보
    public DOBJ CALLminab_goso_info_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLminab_goso_info_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLminab_goso_info_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("SEL3").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("SEL3").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 건별처리
    public DOBJ CALLminab_goso_info_MPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD6");
        VOBJ dvobj = dobj.getRetObject("SEL5");         //비교월정보에서 생성시킨 OBJECT입니다.(CALLminab_goso_info_SEL5)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLminab_goso_info_SEL8(Conn, dobj);           //  휴업여부체크
                ////
                double   CNT = dobj.getGVNumber("CNT")+dobj.getRetObject("SEL8").getRecord().getDouble("CNT");         //최종납입년월
                dobj.setGVValue("CNT",CNT+"");
            }
        }
        return dobj;
    }
    // 휴업여부체크
    public DOBJ CALLminab_goso_info_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLminab_goso_info_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLminab_goso_info_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   YRMN = dobj.getRetObject("R").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_CLSED  WHERE  START_YRMN  <=  :YRMN   \n";
        query +=" AND  :YRMN  <=  END_YRMN   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    // 최종 미징수(미납)정보
    public DOBJ CALLminab_goso_info_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLminab_goso_info_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLminab_goso_info_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   TOT_ADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("TOT_ADDT_AMT");   //총 가산 금액
        String   LAST_YRMN = dobj.getRetObject("SEL3").getRecord().get("LAST_YRMN");   //최종납입년월
        double   TOT_USE_AMT = dvobj.getRecord().getDouble("TOT_USE_AMT");   //총 사용 금액
        double   LAST_CNT = dobj.getRetObject("SEL11").getRecord().getInt("MON_CNT")-dobj.getGVNumber("CNT");   //LAST_CNT
        String   END_YRMN = dobj.getRetObject("SEL3").getRecord().get("END_YRMN");   //종료년월
        double   CNT = dobj.getGVNumber("CNT");   //카운트
        double   TOT_EADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("TOT_EADDT_AMT");   //총 중가산 금액
        double   TOT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("TOT_USE_AMT")+dobj.getRetObject("OSP1").getRecord().getDouble("TOT_ADDT_AMT")+dobj.getRetObject("OSP1").getRecord().getDouble("TOT_EADDT_AMT");   //총 금액
        String   START_YRMN = dobj.getRetObject("SEL3").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :LAST_YRMN  AS  LAST_YRMN  ,  :START_YRMN  AS  START_YRMN  ,  :END_YRMN  AS  END_YRMN  ,  :LAST_CNT  AS  MON_CNT  ,  :CNT  AS  CLOSE_CNT  ,  :TOT_AMT  AS  NONPAY_TOT_AMT  ,  :TOT_USE_AMT  AS  NONPAY_ORG_AMT  ,  :TOT_ADDT_AMT  AS  NONPAY_ADDT_AMT  ,  :TOT_EADDT_AMT  AS  NONPAY_EXT_ADDT_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //총 가산 금액
        sobj.setString("LAST_YRMN", LAST_YRMN);               //최종납입년월
        sobj.setDouble("TOT_USE_AMT", TOT_USE_AMT);               //총 사용 금액
        sobj.setDouble("LAST_CNT", LAST_CNT);               //LAST_CNT
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setDouble("CNT", CNT);               //카운트
        sobj.setDouble("TOT_EADDT_AMT", TOT_EADDT_AMT);               //총 중가산 금액
        sobj.setDouble("TOT_AMT", TOT_AMT);               //총 금액
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 최종고소정보
    public DOBJ CALLminab_goso_info_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLminab_goso_info_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLminab_goso_info_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REQ_DAY  ,  COMPN_DAY  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  REQ_DAY ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 더넣은금액조회
    public DOBJ CALLminab_goso_info_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLminab_goso_info_SEL14(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLminab_goso_info_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(BALANCE,0)  BALANCE  FROM  (   \n";
        query +=" SELECT  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$minab_goso_info
    //##**$$get_demd_amt
    /*
    */
    public DOBJ CTLget_demd_amt(DOBJ dobj)
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
            dobj  = CALLget_demd_amt_SEL1(Conn, dobj);           //  청구데이터 조회
            dobj  = CALLget_demd_amt_OSP1(Conn, dobj);           //  청구 데이터 생성
            dobj  = CALLget_demd_amt_OSP7(Conn, dobj);           //  고소 금액 생성
            dobj  = CALLget_demd_amt_SEL2(Conn, dobj);           //  청구금액 결과 취합
            dobj  = CALLget_demd_amt_SEL4(Conn, dobj);           //  월사용료 지분율 등록정보
            dobj  = CALLget_demd_amt_SEL5(Conn, dobj);           //  요금할인년월조회
            dobj  = CALLget_demd_amt_SEL6(Conn, dobj);           //  통합징수 협회지분율
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
    public DOBJ CTLget_demd_amt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_demd_amt_SEL1(Conn, dobj);           //  청구데이터 조회
        dobj  = CALLget_demd_amt_OSP1(Conn, dobj);           //  청구 데이터 생성
        dobj  = CALLget_demd_amt_OSP7(Conn, dobj);           //  고소 금액 생성
        dobj  = CALLget_demd_amt_SEL2(Conn, dobj);           //  청구금액 결과 취합
        dobj  = CALLget_demd_amt_SEL4(Conn, dobj);           //  월사용료 지분율 등록정보
        dobj  = CALLget_demd_amt_SEL5(Conn, dobj);           //  요금할인년월조회
        dobj  = CALLget_demd_amt_SEL6(Conn, dobj);           //  통합징수 협회지분율
        return dobj;
    }
    // 청구데이터 조회
    // 업소코드, 시작년월, 종료년월을 기준으로 청구데이터를 조회한다
    public DOBJ CALLget_demd_amt_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_demd_amt_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_demd_amt_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   DEMD_GBN = dobj.getRetObject("S").getRecord().get("DEMD_GBN");   //청구 구분
        String   RECV_DAY ="";   //영수 일자
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  NVL(:DEMD_GBN,  'O')  AS  DEMD_GBN  ,  SUBSTR(:START_YRMN,  1,  6)  AS  START_YRMN  ,  SUBSTR(:END_YRMN,  1,  6)  AS  END_YRMN  ,  :RECV_DAY  AS  RECV_DAY  ,  ''  AS  RECV_DAY2  FROM  (   \n";
        query +=" SELECT  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DEMD_GBN", DEMD_GBN);               //청구 구분
        sobj.setString("RECV_DAY", RECV_DAY);               //영수 일자
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 청구 데이터 생성
    // 청구 데이터를 생성하기 위한 프로시저 (GIBU.SP_PROC_DEMDGIRO) 를 호출한다
    public DOBJ CALLget_demd_amt_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL1");         //청구데이터 조회에서 생성시킨 OBJECT입니다.(CALLget_demd_amt_SEL1)
        dvobj.Println("OSP1");
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","DEMD_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
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
        String   spname ="GIBU.SP_GET_DEMD_AMT0";
        int[]    intypes={12, 12, 12, 12, 12};;
        String[] outcolnms={"BSTYP_CD","UPSO_GRAD","MONPRNCFEE","MONPRNCFEE2","DEMD_GBN","DEMD_MMCNT","TOT_USE_AMT","TOT_ADDT_AMT","TOT_EADDT_AMT","DSCT_AMT","TOT_DEMD_AMT"};;
        int[]    outtypes ={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
    // 고소 금액 생성
    public DOBJ CALLget_demd_amt_OSP7(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP7");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL1");         //청구데이터 조회에서 생성시킨 OBJECT입니다.(CALLget_demd_amt_SEL1)
        dvobj.Println("OSP7");
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","CRET_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRET_GBN ="O";         //청구 년월
            record.put("CRET_GBN",CRET_GBN);
            ////
            String   RECV_DAY = dobj.getRetObject("SEL1").getRecord().get("RECV_DAY2");         //영수 일자
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
        String   spname ="GIBU.SP_GET_ACCU_AMT";
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
        robj.setName("OSP7");
        robj.Println("OSP7");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 청구금액 결과 취합
    public DOBJ CALLget_demd_amt_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_demd_amt_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_demd_amt_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MONPRNCFEE2 = dobj.getRetObject("OSP1").getRecord().getDouble("MONPRNCFEE2");   //기준월정료
        double   TOT_ADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("TOT_ADDT_AMT");   //총 가산 금액
        double   TOT_USE_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("TOT_USE_AMT");   //총 사용 금액
        double   TOT_DEMD_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("TOT_DEMD_AMT");   //총 청구 금액
        double   DSCT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("DSCT_AMT");   //할인 금액 - 자동이체시 할인
        double   DEMD_MMCNT = dobj.getRetObject("OSP1").getRecord().getDouble("DEMD_MMCNT");   //청구개월수
        double   USE_AMT = dobj.getRetObject("OSP7").getRecord().getDouble("P_TUSE_AMT");   //사용 금액
        double   MONPRNCFEE = dobj.getRetObject("OSP1").getRecord().getDouble("MONPRNCFEE");   //월정료
        double   ADDT_AMT = dobj.getRetObject("OSP7").getRecord().getDouble("P_TUSE_AMT")*0.15;   //가산 금액
        double   TOT_EADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("TOT_EADDT_AMT");   //총 중가산 금액
        double   TOT_AMT = dobj.getRetObject("OSP7").getRecord().getDouble("P_TUSE_AMT")*1.15;   //총 금액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :MONPRNCFEE  AS  MONPRNCFEE  ,  :MONPRNCFEE2  AS  MONPRNCFEE2  ,  :DEMD_MMCNT  AS  DEMD_MMCNT  ,  :TOT_USE_AMT  AS  TOT_USE_AMT  ,  :TOT_ADDT_AMT  +  :TOT_EADDT_AMT  AS  TOT_ADDT_AMT  ,  :DSCT_AMT  AS  DSCT_AMT  ,  :TOT_DEMD_AMT  AS  TOT_DEMD_AMT  ,  TRUNC(:USE_AMT,  -1)  AS  ACCU_USE_AMT  ,  TRUNC(:ADDT_AMT,  -1)  AS  ACCU_ADDT_AMT  ,  TRUNC(:TOT_AMT,  -1)  AS  ACCU_TOT_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("MONPRNCFEE2", MONPRNCFEE2);               //기준월정료
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //총 가산 금액
        sobj.setDouble("TOT_USE_AMT", TOT_USE_AMT);               //총 사용 금액
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //총 청구 금액
        sobj.setDouble("DSCT_AMT", DSCT_AMT);               //할인 금액 - 자동이체시 할인
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //청구개월수
        sobj.setDouble("USE_AMT", USE_AMT);               //사용 금액
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //월정료
        sobj.setDouble("ADDT_AMT", ADDT_AMT);               //가산 금액
        sobj.setDouble("TOT_EADDT_AMT", TOT_EADDT_AMT);               //총 중가산 금액
        sobj.setDouble("TOT_AMT", TOT_AMT);               //총 금액
        return sobj;
    }
    // 월사용료 지분율 등록정보
    public DOBJ CALLget_demd_amt_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_demd_amt_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_demd_amt_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  APPL_DAY,  RATE  FROM  GIBU.TBRA_FEERATE_HISTY  WHERE  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(:UPSO_CD)  ORDER  BY  APPL_DAY ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 요금할인년월조회
    public DOBJ CALLget_demd_amt_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_demd_amt_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_demd_amt_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUBSTR(DSCT_START,1,4)  ||  '/'  ||  SUBSTR(DSCT_START,5,2)  AS  DSCT_START  ,  SUBSTR(DSCT_END,1,4)  ||  '/'  ||  SUBSTR(DSCT_END,5,2)  AS  DSCT_END  FROM  LOG.KDS_SHOP  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 통합징수 협회지분율
    public DOBJ CALLget_demd_amt_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_demd_amt_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_demd_amt_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  APPL_DAY,  (100  -  MNG_RATE)  AS  RATE  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(:UPSO_CD)  ORDER  BY  APPL_DAY ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$get_demd_amt
    //##**$$upso_rept_detail
    /* * 프로그램명 : bra04_r04
    * 작성자 : 서정재
    * 작성일 : 2009/12/15
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_rept_detail(DOBJ dobj)
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
            dobj  = CALLupso_rept_detail_SEL1(Conn, dobj);           //  입금상세내역
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
    public DOBJ CTLupso_rept_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_rept_detail_SEL1(Conn, dobj);           //  입금상세내역
        return dobj;
    }
    // 입금상세내역
    public DOBJ CALLupso_rept_detail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_rept_detail_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_rept_detail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("NOTE_YRMN");   //원장 년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_AMT  ,  A.COMIS  ,  A.RECV_DAY  ,  D.BANK_NM  ,  A.ACCN_NUM  ,  NVL(B.BALANCE,  0)  BALANCE  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XA.REPT_AMT  ,  XA.COMIS  ,  XA.RECV_DAY  ,  XA.BANK_CD  ,  XA.ACCN_NUM  ,  XB.DISTR_SEQ  FROM  GIBU.TBRA_REPT  XA  ,  GIBU.TBRA_NOTE  XB  WHERE  XB.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XB.NOTE_NUM  =  '0001'   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  XB.REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  XB.REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  XB.REPT_GBN  UNION  ALL   \n";
        query +=" SELECT  XC.UPSO_CD  ,  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XA.DISTR_AMT  ,  XB.COMIS  ,  XB.RECV_DAY  ,  XB.BANK_CD  ,  XB.ACCN_NUM  ,  XA.DISTR_SEQ  FROM  GIBU.TBRA_REPT_UPSO_DISTR  XA  ,  GIBU.TBRA_REPT  XB  ,  GIBU.TBRA_NOTE  XC  WHERE  XC.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XC.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XC.NOTE_NUM  =  '0001'   \n";
        query +=" AND  XA.UPSO_CD  =  XC.UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  XC.REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  XC.REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  XC.REPT_GBN   \n";
        query +=" AND  XA.DISTR_SEQ  =  XC.DISTR_SEQ   \n";
        query +=" AND  XB.REPT_DAY  =  XC.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XC.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XC.REPT_GBN  )  A  ,  (   \n";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  NVL(BALANCE,  0)  BALANCE  FROM  (   \n";
        query +=" SELECT  PROC_DAY  ,  PROC_NUM  ,  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  PROC_DAY  ||  PROC_NUM  <  (   \n";
        query +=" SELECT  PROC  FROM(   \n";
        query +=" SELECT  PROC_DAY  ||  PROC_NUM  AS  PROC  FROM  GIBU.TBRA_REPT_BALANCE  XA  ,  GIBU.TBRA_NOTE  XB  WHERE  XB.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XB.NOTE_NUM  =  '0001'   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  XB.REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  XB.REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  XB.REPT_GBN   \n";
        query +=" AND  NVL(XA.DISTR_SEQ,  '-')  =  NVL(XB.DISTR_SEQ,  '-')  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  WHERE  ROWNUM  =  1  )   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  )  B  ,  (   \n";
        query +=" SELECT  BANK_CD,  BANK_NM  FROM  ACCT.TCAC_BANK  UNION  ALL   \n";
        query +=" SELECT  CODE_CD  AS  BANK_CD,  CODE_NM  AS  BANK_NM  FROM  FIDU.TENV_CODE  WHERE  1=1   \n";
        query +=" AND  HIGH_CD='00495'  )D  WHERE  B.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  D.BANK_CD  =  A.BANK_CD ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //원장 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upso_rept_detail
    //##**$$end
}
