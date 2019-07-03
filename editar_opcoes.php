<?php

$servername = "localhost";

$username = "jsonbusc";

$password = "moveis1234";

if (isset($_GET['nome']) && isset($_GET['som']) && isset($_GET['musica']) && isset($_GET['cara']) && isset($_GET['novonome'])
) {
    $nome = $_GET['nome'];
    $som = $_GET['som'];
    $musica = $_GET['musica'];
    $cara = $_GET['cara'];
    $novonome = $_GET['novonome'];

    $conn = new PDO("mysql:host=$servername;dbname=jsonbusc_moveis", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $conn->beginTransaction();

    try {
        $query = "select utilizador.id_opcoes, utilizador.id_foto, utilizador.id from utilizador where nome=?";
        $sth = $conn->prepare($query);
        $sth->bindParam(1, $nome, PDO::PARAM_STR);
        $sth->execute();
        $valor = array(0, 0, 0);
        while ($row = $sth->fetch(PDO::FETCH_ASSOC)) {
            $valor[0] = $row['id_opcoes'];
            $valor[1] = $row['id_foto'];
            $valor[2] = $row['id_foto'];
        }

        //updates
        $query = "UPDATE opcoes SET som= ? ,musica= ? WHERE id=?";
        $sth = $conn->prepare($query);
        $sth->bindParam(1, $som);
        $sth->bindParam(2, $musica);
        $sth->bindParam(3, $valor[0]);
        $sth->execute();

        $query = "UPDATE foto SET url= ?  WHERE id=?";
        $sth = $conn->prepare($query);
        $sth->bindParam(1, $cara);
        $sth->bindParam(2, $valor[1]);
        $sth->execute();

        $query = "UPDATE utilizador SET nome= ?  WHERE id=?";
        $sth = $conn->prepare($query);
        $sth->bindParam(1, $novonome);
        $sth->bindParam(2, $valor[2]);
        $sth->execute();
            
        $conn->commit();
    } catch (Exception $ex) {
        $conn->rollBack();
        echo "erro";
    }
    
}
?>