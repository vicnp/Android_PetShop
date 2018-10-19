<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

	if(isset($_POST['email'])){
		$db = new DbOperations(); 

    		if($db->isEmailExist($_POST['email'])){
            		   
            $to      = $_POST['email'];
            $subject = 'Email de recuperação de senha.';
            $message = 'Este email deve recuperar sua senha';
            $headers = 'From: PetShopApp' . "\r\n" .
                'Reply-To: webmaster@example.com' . "\r\n" .
                'X-Mailer: PHP/' . phpversion();
            mail($to, $subject, $message, $headers);

			$response['error'] = false;
			$response['message'] = "Email enviado com sucesso.";
		}else{
			$response['error'] = true; 
			$response['message'] = "Usuário não contratado.";			
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Preencha o campo de email.";
	}


echo json_encode($response);