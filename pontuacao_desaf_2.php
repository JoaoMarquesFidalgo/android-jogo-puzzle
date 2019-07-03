<?php

$servername = "localhost";

$username = "jsonbusc";

$password = "moveis1234";

if (isset($_GET['pontuacao']) && isset($_GET['tempo'])&& isset($_GET['pontuacao_desaf_2'])&& isset($_GET['nome'])) {
    $pontuacao = $_GET['pontuacao'];
    $tempo = $_GET['tempo'];
    $pontuacao_desaf_2 = $_GET['pontuacao_desaf_2'];
    $nome = $_GET['nome'];
   
    
    $conn = new PDO("mysql:host=$servername;dbname=jsonbusc_moveis", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
   
    
    $query = "select utilizador.id_opcoes from utilizador where nome=?";
        $sth = $conn->prepare($query);
        $sth->bindParam(1, $nome, PDO::PARAM_STR);
        $sth->execute();
        $valor = array(0);
        while ($row = $sth->fetch(PDO::FETCH_ASSOC)) {
            $valor[0] = $row['id_opcoes'];
        }
    
    $query = "UPDATE pontuacao SET pontuacao=?,tempo=?,pontuacao_desaf_2=? WHERE id=?";
        $sth = $conn->prepare($query);
        $sth->bindParam(1, $pontuacao);
        $sth->bindParam(2, $tempo);
        $sth->bindParam(3, $pontuacao_desaf_2);
        $sth->bindParam(4, $valor[0]);
        $sth->execute();
         
        echo "sucesso";
    
}
?>