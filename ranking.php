<?php

//header('Content-type: application/json');
$servername = "localhost";

$username = "jsonbusc";

$password = "moveis1234";


$conn = new PDO("mysql:host=$servername;dbname=jsonbusc_moveis", $username, $password);
// set the PDO error mode to exception
$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

//selects
$query = "select utilizador.nome as utilizador_nome, planeta.nome as planeta_nome, pontuacao.pontuacao
            from utilizador
            INNER JOIN planeta on utilizador.id_planeta = planeta.id
            INNER JOIN pontuacao on utilizador.id_pontuacao = pontuacao.id
            Order by pontuacao.pontuacao DESC
            LIMIT 5";
$sth = $conn->prepare($query);
$sth->execute();

///////

$rows = array();
//retrieve and print every record
while ($r = $sth->fetch(PDO::FETCH_ASSOC)) {
    // $rows[] = $r; has the same effect, without the superfluous data attribute
    $rows[] = $r;
}

// now all the rows have been fetched, it can be encoded
echo json_encode($rows);
















//echo json_encode($row);
?>