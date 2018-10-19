<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['username']) and isset($_POST['password'])){
		$db = new DbOperations(); 

		if($db->userLogin($_POST['username'], $_POST['password'])){
			$user = $db->getUserByUsername($_POST['username']);
			//$response['error'] = false; 
			//$response['id'] = '1';
			//$response['email'] = 'testemail';
			//$response['username'] = 'testeusrname';

			$response['error'] = false; 
			$response['id'] = $user['id'];
			$response['email'] = $user['email'];
			$response['username'] = $user['username'];
			$response['petname'] = $user['petname'];
			$response['nome'] = $user['nome'];
			$response['sobrenome'] = $user['sobrenome'];
			$response['cpf'] = $user['cpf'];
			$response['cidade'] = $user['cidade'];
			$response['bairro'] = $user['bairro'];
			$response['rua'] = $user['rua'];
			$response['cep'] = $user['cep'];
			$response['numero'] = $user['numero'];
			$response['telefoneUm'] = $user['telefoneUm'];
			$response['telefoneDois'] = $user['telefoneDois'];
			$response['peso'] = $user['peso'];
			$response['raca'] = $user['raca'];
			$response['ano_nascimento'] = $user['ano_nascimento'];
			

		}else{
			$response['error'] = true; 
			$response['message'] = "Usuário ou senha inválidos!";			
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Preencha todos os campos!";
	}
}

echo json_encode($response);