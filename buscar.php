<?php
header('Content-type: application/json');

$servername = "localhost";

$username = "jsonbusc";

$password = "moveis1234";

try {
	
    if(isset($_GET['nome'])){
        $nome = $_GET['nome'];
    }else{
        $nome = "joao";    
    }
    $conn = new PDO("mysql:host=$servername;dbname=jsonbusc_moveis", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
$query = 
"select utilizador.id as utilizador_id, utilizador.nome as utilizador_nome, utilizador.facebook as utilizador_facebook, utilizador.id_planeta as utilizador_id_planeta, utilizador.id_pontuacao as utilizador_id_pontuacao, utilizador.id_opcoes as utilizador_id_opcoes, utilizador.id_foto as utilizador_id_foto,
planeta.id as planeta_id,
planeta.nome as planeta_nome,
planeta.dificuldade as planeta_dificuldade,
pontuacao.id as pontuacao_id,
pontuacao.pontuacao as pontuacao_pontuacao,
pontuacao.tempo as pontuacao_tempo,
pontuacao.ajuda as pontuacao_ajuda,
pontuacao_desaf_1 as pontuacao_desaf_1,
pontuacao_desaf_2 as pontuacao_desaf_2,
pontuacao_desaf_3 as pontuacao_desaf_3,
pontuacao_desaf_4 as pontuacao_desaf_4,
pontuacao_desaf_5 as pontuacao_desaf_5,
pontuacao_desaf_6 as pontuacao_desaf_6,
pontuacao_desaf_7 as pontuacao_desaf_7,
pontuacao_desaf_8 as pontuacao_desaf_8,
opcoes.id as opcoes_id,
opcoes.som as opcoes_som,
opcoes.musica as opcoes_musica,
opcoes.url as opcoes_url,
foto.id as foto_id,
foto.url as foto_url
from utilizador
INNER JOIN planeta on utilizador.id_planeta = planeta.id
INNER JOIN pontuacao on utilizador.id_pontuacao = pontuacao.id
INNER JOIN opcoes on utilizador.id_opcoes = opcoes.id
INNER JOIN foto on utilizador.id_foto = foto.id
where utilizador.nome=:nome";
    $stmt = $conn->prepare($query);
    $stmt->bindValue(':nome', $nome);
    $stmt->execute();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC))
    {   
        echo json_encode($row);
    }
    }
catch(PDOException $e)
    {
    echo "Connection failed: " . $e->getMessage();
    }

?>