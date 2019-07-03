<?php
header('Content-Type: application/json');

$servername = "localhost";

$username = "jsonbusc";

$password = "moveis1234";

    if(isset($_GET['nome']) || isset($_GET['facebook'])){
        
    $nome = $_GET['nome'];
    $facebook = $_GET['facebook'];
        
    $conn = new PDO("mysql:host=$servername;dbname=jsonbusc_moveis", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $conn->beginTransaction();
    
    try{
        $query = "insert into foto (foto.url)
                values ('carahattrick1.png')";
    $conn->exec($query);
    $last_foto_id = $conn->lastInsertId();
    $last_foto_id = intval($last_foto_id);
    
    $query = "insert into opcoes (opcoes.som, opcoes.musica, opcoes.url)
                values (0, 'a_97202_mp4', 'song1.mp3')";
    $conn->exec($query);
    $last_opcoes_id = $conn->lastInsertId();
    $last_opcoes_id = intval($last_opcoes_id);
    
    $query = "insert into pontuacao (pontuacao.pontuacao, pontuacao.tempo, pontuacao.ajuda, 
pontuacao.pontuacao_desaf_1, pontuacao.pontuacao_desaf_2,pontuacao.pontuacao_desaf_3,pontuacao.pontuacao_desaf_4,
pontuacao.pontuacao_desaf_5,pontuacao.pontuacao_desaf_6,pontuacao.pontuacao_desaf_7,pontuacao.pontuacao_desaf_8)
                values (0, '0', 0,0,0,0,0,0,0,0,0)";
    $conn->exec($query);
    $last_pontuacao_id = $conn->lastInsertId();
    $last_pontuacao_id = intval($last_pontuacao_id);
    
    $number = 1;
    $query = "INSERT into utilizador(utilizador.nome, utilizador.facebook, utilizador.id_planeta, 
        utilizador.id_pontuacao, utilizador.id_opcoes, utilizador.id_foto)
    VALUES (?, ?, ?, ?, ?, ?);";
    $sth = $conn->prepare($query);
    $sth->bindParam(1, $nome, PDO::PARAM_STR);
    $sth->bindParam(2, $facebook, PDO::PARAM_STR);
    $sth->bindParam(3, $number, PDO::PARAM_INT);
    $sth->bindParam(4, $last_pontuacao_id, PDO::PARAM_INT);
    $sth->bindParam(5, $last_opcoes_id, PDO::PARAM_INT);
    $sth->bindParam(6, $last_foto_id, PDO::PARAM_INT);
    $sth->execute();
    $last_id = $conn->lastInsertId();
    $conn->commit();
    
    print(json_encode(array("message" => "sucesso")));
    

    } catch (Exception $ex) {
        echo $ex->getMessage();
    }
    
    }else{
        echo "nada";  
    }
    
?>