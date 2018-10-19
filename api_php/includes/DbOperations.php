<?php 

	class DbOperations{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();

		}

		/*CRUD -> C -> CREATE */

		public function createUser($username, $pass, $email){
			if($this->isUserExist($username,$email)){
				return 0; 
			}else{
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `users` (`id`, `username`, `password`, `email`) VALUES (NULL, ?, ?, ?);");
				$stmt->bind_param("sss",$username,$password,$email);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}

		public function userLogin($username, $pass){
			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT id FROM users WHERE username = ? AND password = ?");
			$stmt->bind_param("ss",$username,$password);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

		public function getUserByUsername($username){
			//$stmt = $this->con->prepare("SELECT * FROM users WHERE username = ?");
			$stmt = $this->con->prepare("SELECT u.username, u.password, u.id, p.nome,p.sobrenome,p.cpf,e.cidade,e.bairro,e.rua,e.cep,e.numero,c.email,c.telefoneUm,c.telefoneDois,a.nome AS petname,a.peso,a.raca,a.ano_nascimento,cs.data,cs.hora FROM users u JOIN profile p ON p.id = u.id JOIN endereco e ON e.profile_id = p.id JOIN contato c ON c.profile_id = p.id JOIN animal a on a.id = p.id JOIN consultas cs ON cs.animal_id = a.id WHERE u.username = ?");
			$stmt->bind_param("s",$username);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}
		

		private function isUserExist($username, $email){
			$stmt = $this->con->prepare("SELECT id FROM users WHERE username = ? OR email = ?");
			$stmt->bind_param("ss", $username, $email);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		
	   public function isEmailExist($email){
			$stmt = $this->con->prepare("SELECT id FROM users WHERE email = ?");
			$stmt->bind_param("s", $email);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

	}