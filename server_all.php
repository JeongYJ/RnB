//MyStory.php


<?php
header("Content-Type: text/html; charset=utf-8");
$connect=mysql_connect("","","") or die("SQL server connected fail"); //connect
        mysql_query("set names utf8", $connect);
        mysql_select_db("rnb5", $connect);      //connect

        $UserCheck = false;     //usercheck
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $sql_search = "select uno, rno from User, UserState where uno = runo and uemail = '".$Data['uemail']."' ORDER BY rdate, rtime desc limit 1" ;        //query
        $result = mysql_query($sql_search, $connect) or die(mysql_error());     //result
        $rows = array();        //array
         $sql = "INSERT INTO UserStory (srno, stitle, stext, sshow) VALUES ('$result[1]','".$Data['stitle']."','".$Data['stext']."','".$Data['sshow']."');"; //query

        if(mysql_num_rows($result) == 0)        //conditi
        {
                printf("{uno : %d, rno : %d}", $result[0], $result[1]);

                $result = mysql_query($sql, $connect) or die(mysql_error());    //result
                        $UserCheck = true;      //usercheck
                        $rows['UserCheck'] = $UserCheck;        //save
                        echo json_encode($rows);        //encode

       }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>


//join.php

<?php
 header("Content-Type: text/html; charset=utf-8");
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect

    mysql_query("set names utf8", $connect);
    mysql_select_db("rnb5", $connect);      //connect

 $UserCheck = false;     //usercheck
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
            $sql_search = "select * from User where uemail = '".$Data['uemail']."'"; //query      //query
        $result = mysql_query($sql_search, $connect) or die(mysql_error());     //result
        $rows = array();        //array

   $sql = "INSERT INTO User (uname, uemail, upasswd) VALUES ('".$Data['uname']."','".$Data['uemail']."','".$Data['upasswd']."');"; //query

        $sql_uno = "select uno from User where uemail = '".$Data['uemail']."'";
        if(mysql_num_rows($result) == 0)
        {
 $result = mysql_query($sql, $connect) or die(mysql_error());     //result
        $rows = array();        //array

                           $UserCheck= true;      //usercheck
                           $rows['UserCheck'] = $UserCheck;        //save
                          echo json_encode($rows);        //encode


        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>




//myStory_Comments.php


<?php
        header("Content-Type: text/html; charset=utf-8");
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect
        mysql_select_db("rnb5", $connect);      //connect


        mysql_query("set names utf8", $connect);

        $UserCheck = false;
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $query_search = "SELECT nno, nsno, nwho, ntext FROM UserState, UserStory,User, Comments WHERE uno = runo and rno = srno and runo = suno and sno = nsno and nwho= '$Data[uno]' and nsno= '$Data[sno]'";

        $result  = mysql_query($query_search, $connect) or die(mysql_error());  //result
        $res = array();        //array
        if(mysql_num_rows($result))     //condition
        {
                while($row = mysql_fetch_array($result, MYSQL_NUM))
                {
                        $jsonObject = (array(
                        'nno'=>$row[0],
                        'nsno'=>$row[1],
                        'nwho'=>$row[2],
                        'ntext'=>$row[3]));
                        $res[] = $jsonObject;

                }
                $UserCheck= true;      //usercheck
                $rows['UserCheck'] = $UserCheck;        //save
                $rows['Data'] = $res;
                echo json_encode($rows);        //encode
        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>



//changePW.php


<?php
header("Content-Type: text/html; charset=utf-8");
$connect=mysql_connect("","","") or die("SQL server connected fail"); //connect
        mysql_query("set names utf8", $connect);
        mysql_select_db("rnb5", $connect);      //connect

        $UserCheck = false;     //usercheck
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $sql_search = "select uno from User where uemail = '".$Data['uemail']."'";      //query
        $result = mysql_query($sql_search, $connect) or die(mysql_error());     //result

        if(mysql_num_rows($result))         //condition
        {
                while($row = mysql_fetch_array($result, MYSQL_NUM))
                {
                        $uno = $row[0];
                }
                $sql ="UPDATE User SET upasswd = '".$Data['newpasswd']."' where uno =".$uno.";";
                $result = mysql_query($sql, $connect) or die(mysql_error());    //result
                $UserCheck = true;      //usercheck
                $rows['UserCheck'] = $UserCheck;        //save
                echo json_encode($rows);        //encode
        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>




//join_cancel.php

<?php
        header("Content-Type: text/html; charset=utf-8");
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect

        mysql_query("set names utf8", $connect);
        mysql_select_db("rnb5", $connect);      //connect

        $UserCheck = false;     //usercheck
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $query_search = "select uno from User where uemail = '".$Data['uemail']."'"; //query
        $result  = mysql_query($query_search, $connect) or die(mysql_error());  //result

        $sql = "delete from Comments where uemail = '".$Data['uemail']."' and uno=runo and rno=srno and sno=nsno;";  //Comments 제거
        echo $sql;
//      $sql2 = "delete from UserStory where uemail = '".$Data['uemail']."' and uno=runo and rno=srno;";
  //      $sql3 = "delete from UserState where uemail = '".$Data['uemail']."' and uno=runo;";
    //    $sql4 = "delete from User where uemail = '".$Data['uemail']."';"; //기존 회원과 관련된 정보를 모두 제거해야 함.

        echo $sql;
//      echo $sql2;


        if(mysql_num_rows($result))     //condition
        {
                $result = mysql_query($sql, $connect) or die(mysql_error());
  //              $result = mysql_query($sql2, $connect) or die(mysql_error());
    //            $result = mysql_query($sql3, $connect) or die(mysql_error());
      //          $result = mysql_query($sql4 $connect) or die(mysql_error());

                $UserCheck= true;      //usercheck
                $rows['UserCheck'] = $UserCheck;        //save
                echo json_encode($rows);        //encode
        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>




//myStory_Comments_insert.php


<?php
         header("Content-Type: text/html; charset=utf-8");
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect

          mysql_query("set names utf8", $connect);
          mysql_select_db("rnb5", $connect);      //connect

        $UserCheck = false;     //usercheck
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $sql_search = "SELECT uno from User, UserState where uno = runo and uemail = '".$Data['uemail']."' ORDER BY rdate desc limit 1"; //query      //query
        $result = mysql_query($sql_search, $connect) or die(mysql_error());     //result
        $rows = array();        //array


        if(mysql_num_rows($result) == 0)
        {
                $sql = "INSERT INTO Comments(nsno, nwho, ntext) VALUES (".$Data['sno'].",".$Data['uno'].",'".$Data['ntext']."');";

                  $result = mysql_query($sql, $connect) or die(mysql_error());     //result

                  $UserCheck= true;      //usercheck
                  $rows['UserCheck'] = $UserCheck;        //save
                  echo json_encode($rows);        //encode
        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>



//getDetails.php

<?php
    header("Content-Type: text/html; charset=utf-8");
    $conn = mysql_connect("", "", "") or die("SQL server connected fail");
    mysql_query("set names utf8", $conn);
    mysql_select_db("rnb5", $conn);

    $UserCheck = false;
    $tmp = file_get_contents('php://input');
    $Data = json_decode($tmp, true);

    $sql = "select dno, deno, dred, dgreen, dblue, diconpath from Details where ddetails = '".$Data['ddetails']."';";

    //dno, deno, dred, dgreen, dblue, diconpath

    $res = mysql_query($sql, $conn) or die(mysql_error());
    $result = array();

    if(mysql_num_rows)
    {
        $UserCheck = true;
        while($row = mysql_fetch_array($res, MYSQL_NUM))
{
        $rows['dno'] = $row[0];
        $rows['deno'] = $row[1];
        $rows['dred'] = $row[2];
        $rows['dgreen'] = $row[3];
        $rows['dblue'] = $row[4];
        $rows['diconpath'] = $row[5];
}
       $rows['UserCheck'] = $UserCheck;
        echo json_encode($rows);
    }
    else
    {
        $rows['UserCheck'] = $UserCheck;
        echo json_encode($rows);
    }

    mysql_close($conn);
?>



//login.php

<?php
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect

        mysql_select_db("rnb5", $connect);      //connect

        $UserCheck = false;     //usercheck
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $query_search = "select uno from User where uemail = '".$Data['uemail']."' AND upasswd = '".$Data['upasswd']."'"; //query
        $result  = mysql_query($query_search, $connect) or die(mysql_error());  //result
        $res = array();        //array
        //      printf("로그인");
        if(mysql_num_rows($result))     //condition
        {
                while($row = mysql_fetch_array($result, MYSQL_NUM))
                {
                        $rows['uno'] = $row[0];
                        //$rows['UserInfo'] = $row;
                }
                $UserCheck= true;      //usercheck
                $rows['UserCheck'] = $UserCheck;        //save
                echo json_encode($rows);        //encode
        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>



//myStory_insert.php


<?php
         header("Content-Type: text/html; charset=utf-8");
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect

          mysql_query("set names utf8", $connect);
          mysql_select_db("rnb5", $connect);      //connect

        $UserCheck = false;     //usercheck
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $sql_search = "SELECT uno from User, UserState where uno = runo and uemail = '".$Data['uemail']."' ORDER BY rdate desc limit 1"; //query      //query
        $result = mysql_query($sql_search, $connect) or die(mysql_error());     //result
        $rows = array();        //array


        if(mysql_num_rows($result) == 0)
        {

                $sql = "INSERT INTO UserStory(suno, srno, stitle, stext, sshow) VALUES (".$Data['suno'].",".$Data['srno'].",'".$Data['stitle']."','".$Data['stext']."',".$Data['sshow'].");";

                  $result = mysql_query($sql, $connect) or die(mysql_error());     //result

                  $UserCheck= true;      //usercheck
                  $rows['UserCheck'] = $UserCheck;        //save
                  echo json_encode($rows);        //encode


        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>



//myMusic.php


<?php
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect

        mysql_select_db("rnb5", $connect);      //connect

        $stats = false;
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode

        $query_search = "select * from Music;"; //query
        $result  = mysql_query($query_search, $connect) or die(mysql_error());  //result
        $res = array();        //array
        //      printf("로그인");
        if(mysql_num_rows($result))     //condition
        {
                while($row = mysql_fetch_array($result, MYSQL_NUM))
                {
                        $jsonObject = (array(
                        'mno'=>$row[0],
                        'memo'=>$row[1],
                        'mtitle'=>$row[2],
                        'mkind'=>$row[3],
                        'mmusician'=>$row[4]));
                        $res[] = $jsonObject;

                }
                $UserCheck= true;      //usercheck
                $rows['UserCheck'] = $UserCheck;        //save
                $rows['Data'] = $res;
                echo json_encode($rows);        //encode
        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>




//myStory_insert_.php


<?php
    header("Content-Type: text/html; charset=utf-8");
    $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect
        mysql_query("set names utf8", $connect);
        mysql_select_db("rnb5", $connect);      //connect

    $UserCheck = false;     //usercheck
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $sql_search = "select uno, rno from User,UserState where uno=runo and uemail = '".$Data['uemail']."' ORDER BY rdate desc limit 1"; //query      //query
        $result = mysql_query($sql_search, $connect) or die(mysql_error());     //result
        $rows = array();        //array

        if(mysql_num_rows($result) == 0)
        {
                while($row = mysql_fetch_array($result,MYSQL_NUM))
                {
                    $uno = $row[0];
                    $rno = $row[1];
                }

                  $sql = "INSERT INTO UserStory(suno, srno, stitle, stext, sshow) VALUES(".$uno.",".$rno.",'".$Data['stitle']."','".$Data['stext']."',".$Data['sshow'].");"; //query

                  $result = mysql_query($sql, $connect) or die(mysql_error());     //result

                  $UserCheck= true;      //usercheck
                  $rows['UserCheck'] = $UserCheck;        //save
                  echo json_encode($rows);        //encode


        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>



//getUserState.php




<?php
 header("Content-Type: text/html; charset=utf-8");
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect

    mysql_query("set names utf8", $connect);
    mysql_select_db("rnb5", $connect);      //connect

        $UserCheck = false;     //usercheck
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $sql_search = "SELECT rdate,rdno,reno,runo,rno from UserState where runo = (select uno from User where uemail = '".$Data['uemail']."') ORDER BY rdate DESC limit 1";
        $result = mysql_query($sql_search, $connect) or die(mysql_error());     //result

        if(mysql_num_rows($result))
        {

                while($row = mysql_fetch_array($result, MYSQL_NUM))
                {
                        $user_date = $row[0];
                        $rows['rdno'] = $row[1];
                        $rows['reno'] = $row[2];
                        $rows['runo'] = $row[3];
                        $rows['rno'] = $row[4];
                }
                $today = date("Y-m-d");
                $str = strcmp($today, $user_date);

                if(!$str)
                {

                     $UserCheck = true;      //usercheck //오늘 날짜가 이미 존재
한다면 => 오늘은 기분 선택을 더이상 하지 못하도록

                        $rows['UserCheck'] = $UserCheck;
                        echo json_encode($rows);
                }
                else
                {
                    $UserCheck = false;
                        $rows['UserCheck'] = $UserCheck;        //save
                        echo json_encode($rows);
                }


        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }  
        
        mysql_close($connect);  //disconnect
?>



//myMusic_select.php

<?php
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect

        mysql_select_db("rnb5", $connect);      //connect

        $stats = false;
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode

        $query_search = "SELECT mno, meno, mtitle, mkind, mmusician FROM Music, Emotion WHERE eno = meno and eno = (SELECT reno FROM User, UserState WHERE uno = runo and uemail= '".$Data['uemail']."' ORDER BY rdate desc limit 1)";
//      $query_search = "select meno from Music,Emotion where eno=meno and eno=".$Data['eno'].";"; //query
        $result  = mysql_query($query_search, $connect) or die(mysql_error());  //result
        $res = array();        //array
        //      printf("로그인");
        if(mysql_num_rows($result))     //condition
        {
                while($row = mysql_fetch_array($result, MYSQL_NUM))
                {
                        $jsonObject = (array(
                        'mno'=>$row[0],
                        'memo'=>$row[1],
                        'mtitle'=>$row[2],
                        'mkind'=>$row[3],
                        'mmusician'=>$row[4]));
                        $res[] = $jsonObject;

                }
                $UserCheck= true;      //usercheck
                $rows['UserCheck'] = $UserCheck;        //save
                $rows['Data'] = $res;
                echo json_encode($rows);        //encode
        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>




//setUserState.php


<?php
header("Content-Type: text/html; charset=utf-8");
$connect=mysql_connect("","","") or die("SQL server connected fail"); //connect
        mysql_query("set names utf8", $connect);
        mysql_select_db("rnb5", $connect);      //connect

        $UserCheck = false;     //usercheck
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $sql_search = "select uno from User where uemail = '".$Data['uemail']."'";       //query
        $result = mysql_query($sql_search, $connect) or die(mysql_error());     //result
        $rows = array();        //array

        #$sql = "INSERT INTO UserState(runo, reno, rdno) VALUES(".$result[0].",".$Data['reno'].",".$Data['rdno'].");"; //query
        #echo $sql;
    if(mysql_num_rows($result))        //condition
    {

        while($row = mysql_fetch_array($result, MYSQL_NUM))
        {
                $uno = $row[0];
        }

        $sql = "INSERT INTO UserState(runo, reno, rdno) VALUES(".$uno.",".$Data['reno'].",".$Data['rdno'].");"; //query

        $result = mysql_query($sql, $connect) or die(mysql_error());    //result

                $UserCheck = true;      //usercheck
                $rows['UserCheck'] = $UserCheck;        //save
                echo json_encode($rows);        //encode
        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>




//getUserState_graph.php


<?php
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect

        mysql_select_db("rnb5", $connect);      //connect
        $UserCheck = false;
         $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode

        $query_search = "select rdno from UserState,User where uno=runo and uemail='".$Data['uemail']."'";
        $result  = mysql_query($query_search, $connect) or die(mysql_error());  //result

        if(mysql_num_rows($result))     //condition
        {
                while($row = mysql_fetch_array($result, MYSQL_NUM))
                {
                        $jsonObj = (array(
                        'rdno'=>$row[0]));
                        $res[] = $jsonObj;
                }
                $UserCheck= true;      //usercheck
                $rows['UserCheck'] = $UserCheck;        //save
                $rows['Data'] = $res;
                echo json_encode($rows);        //encode
         }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>





//myStory.php

?php
 header("Content-Type: text/html; charset=utf-8");
        $connect=mysql_connect("115.68.182.138","root","test1234") or die("SQL server connected fail"); //connect
        mysql_select_db("rnb5", $connect);      //connect


    mysql_query("set names utf8", $connect);

        $stats = false;
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $query_search = "SELECT sdate, stitle, stext, sshow, rdno, sno FROM UserState, UserStory ,User WHERE uno = runo and rno = srno and runo = suno and uemail= '".$Data['uemail']."' Group by stitle ORDER BY sdate, stime desc";
        $result  = mysql_query($query_search, $connect) or die(mysql_error());  //result
        $res = array();        //array
        //      printf("로그인");
        if(mysql_num_rows($result))     //condition
        {
                while($row = mysql_fetch_array($result, MYSQL_NUM))
                {
                        $jsonObject = (array(
                        'sdate'=>$row[0],
                        'stitle'=>$row[1],
                        'stext'=>$row[2],
                        'sshow'=>$row[3],
                        'rdno'=>$row[4],
                        'sno'=>$row[5]));
                        $res[] = $jsonObject;

                }
                $UserCheck= true;      //usercheck
                $rows['UserCheck'] = $UserCheck;        //save
                $rows['Data'] = $res;
                echo json_encode($rows);        //encode
        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>




///join.php 


<?php
 header("Content-Type: text/html; charset=utf-8");
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect

    mysql_query("set names utf8", $connect);
    mysql_select_db("rnb5", $connect);      //connect

 $UserCheck = false;     //usercheck
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
            $sql_search = "select * from User where uemail = '".$Data['uemail']."'"; //query      //query
        $result = mysql_query($sql_search, $connect) or die(mysql_error());     //result
        $rows = array();        //array

   $sql = "INSERT INTO User (uname, uemail, upasswd) VALUES ('".$Data['uname']."','".$Data['uemail']."','".$Data['upasswd']."');"; //query

        $sql_uno = "select uno from User where uemail = '".$Data['uemail']."'";
        if(mysql_num_rows($result) == 0)
        {
 $result = mysql_query($sql, $connect) or die(mysql_error());     //result
        $rows = array();        //array

                           $UserCheck= true;      //usercheck
                           $rows['UserCheck'] = $UserCheck;        //save
                          echo json_encode($rows);        //encode


        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>





//myStory_Comments.php

<?php
        header("Content-Type: text/html; charset=utf-8");
        $connect=mysql_connect("","","") or die("SQL server connected fail"); //connect
        mysql_select_db("rnb5", $connect);      //connect


        mysql_query("set names utf8", $connect);

        $UserCheck = false;
        $tmp = file_get_contents('php://input');        //decode
        $Data = json_decode($tmp, true);        //decode
        $query_search = "SELECT nno, nsno, nwho, ntext FROM UserState, UserStory,User, Comments WHERE uno = runo and rno = srno and runo = suno and sno = nsno and nwho= '$Data[uno]' and nsno= '$Data[sno]'";

        $result  = mysql_query($query_search, $connect) or die(mysql_error());  //result
        $res = array();        //array
        if(mysql_num_rows($result))     //condition
        {
                while($row = mysql_fetch_array($result, MYSQL_NUM))
                {
                        $jsonObject = (array(
                        'nno'=>$row[0],
                        'nsno'=>$row[1],
                        'nwho'=>$row[2],
                        'ntext'=>$row[3]));
                        $res[] = $jsonObject;

                }
                $UserCheck= true;      //usercheck
                $rows['UserCheck'] = $UserCheck;        //save
                $rows['Data'] = $res;
                echo json_encode($rows);        //encode
        }
        else
        {
                $rows['UserCheck'] = $UserCheck;
                echo json_encode($rows);
        }

        mysql_close($connect);  //disconnect
?>









