
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p09
{
    public bra06_p09()
    {
    }
    //##**$$upso_visit_list
    /* * 프로그램명 : bra06_p09
    * 작성자 : 서정재
    * 작성일 : 2009/12/02
    * 설명 :
    업소코드를 기준으로 업소방문등록 데이타를 조회한다.
    조회대상: JOB_GBN IN('M', 'P', 'T','E','R')
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_visit_list(DOBJ dobj)
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
            dobj  = CALLupso_visit_list_SEL1(Conn, dobj);           //  업소정보 조회
            if( dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("ALL"))
            {
                dobj  = CALLupso_visit_list_SEL2(Conn, dobj);           //  전체방문내역리스트
                dobj  = CALLupso_visit_list_MRG5( dobj);        //  결과취합
            }
            else
            {
                dobj  = CALLupso_visit_list_SEL3(Conn, dobj);           //  보고용방문내역리스트
                dobj  = CALLupso_visit_list_MRG5( dobj);        //  결과취합
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
    public DOBJ CTLupso_visit_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_visit_list_SEL1(Conn, dobj);           //  업소정보 조회
        if( dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("ALL"))
        {
            dobj  = CALLupso_visit_list_SEL2(Conn, dobj);           //  전체방문내역리스트
            dobj  = CALLupso_visit_list_MRG5( dobj);        //  결과취합
        }
        else
        {
            dobj  = CALLupso_visit_list_SEL3(Conn, dobj);           //  보고용방문내역리스트
            dobj  = CALLupso_visit_list_MRG5( dobj);        //  결과취합
        }
        return dobj;
    }
    // 업소정보 조회
    public DOBJ CALLupso_visit_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_visit_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.MNGEMSTR_NM  ,  DECODE(XA.MNGEMSTR_RESINUM,NULL,'',SUBSTR(XA.MNGEMSTR_RESINUM,0,6)||'-'||SUBSTR(XA.MNGEMSTR_RESINUM,7,7))  MNGEMSTR_RESINUM  ,  XA.PERMMSTR_NM  ,  DECODE(XA.PERMMSTR_RESINUM,NULL,'',SUBSTR(XA.PERMMSTR_RESINUM,0,6)||'-'||SUBSTR(XA.PERMMSTR_RESINUM,7,7))  PERMMSTR_RESINUM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.OPBI_DAY  ,  XA.NEW_DAY  ,  XA.STAFF_CD  ,  XC.HAN_NM  STAFF_NM  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_HPNUM  ,  XA.PERMMSTR_HPNUM  ,  XA.UPSO_NEW_ZIP  UPSO_ZIP  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  decode(XA.BIOWN_NUM,  null,  '',  substr(XA.BIOWN_NUM,0,3)  ||  '-'  ||  substr(XA.BIOWN_NUM,4,2)  ||  '-'  ||  substr(XA.BIOWN_NUM,6,5)  )  BIOWN_NUM  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  INSA.TINS_MST01  XC  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XA.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 전체방문내역리스트
    public DOBJ CALLupso_visit_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_visit_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.VISIT_DAY  ,  A.JOB_GBN  ,  B.CODE_NM  JOB_NM  ,  1  VISIT_NUM  ,  A.REMAK  ,  DECODE(LENGTH(A.VISIT_TIME),  4,  SUBSTR(A.VISIT_TIME,1,2)||':'||SUBSTR(A.VISIT_TIME,3,2),  '')  AS  VISIT_TIME  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  FIDU.TENV_CODE  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.JOB_GBN  IN  ('V','M','P','T','E','R','C','D','U')   \n";
        query +=" AND  B.HIGH_CD  =  '00198'   \n";
        query +=" AND  A.JOB_GBN  =  B.CODE_CD  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  ,  VISIT_DAY  ,  JOB_GBN  ,  ''  JOG_NM  ,  VISIT_NUM+1  VISIT_NUM  ,  REMAK  ,  ''  AS  VISIT_TIME  FROM  GIBU.TBRA_UPSO_VISIT_BRE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  JOB_GBN  IN  ('V','M','P','T','E','R','C','D','U')  ORDER  BY  VISIT_DAY,  JOB_GBN,  VISIT_NUM ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 결과취합
    public DOBJ CALLupso_visit_list_MRG5(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG5");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL2, SEL3","");
        rvobj.setName("MRG5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 보고용방문내역리스트
    public DOBJ CALLupso_visit_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_visit_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.VISIT_DAY  ,  A.JOB_GBN  ,  B.CODE_NM  JOB_NM  ,  1  VISIT_NUM  ,  A.REMAK  ,  DECODE(LENGTH(A.VISIT_TIME),  4,  SUBSTR(A.VISIT_TIME,1,2)||':'||SUBSTR(A.VISIT_TIME,3,2),  '')  AS  VISIT_TIME  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  FIDU.TENV_CODE  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.JOB_GBN  IN  ('V','M','P','T','E','R')   \n";
        query +=" AND  B.HIGH_CD  =  '00198'   \n";
        query +=" AND  A.JOB_GBN  =  B.CODE_CD  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  ,  VISIT_DAY  ,  JOB_GBN  ,  ''  JOG_NM  ,  VISIT_NUM+1  VISIT_NUM  ,  REMAK  ,  ''  AS  VISIT_TIME  FROM  GIBU.TBRA_UPSO_VISIT_BRE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  JOB_GBN  IN  ('V','M','P','T','E','R')  ORDER  BY  VISIT_DAY,  JOB_GBN,  VISIT_NUM ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$upso_visit_list
    //##**$$end
}
