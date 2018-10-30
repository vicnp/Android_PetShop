<?php 

	class DbOperations{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();

		}

		/*CRUD -> C -> CREATE */

		public function createUser($username, $pass, $email, $cpf, $nome, $sobrenome,$cidade,$bairro,$rua,$cep,$numero,$telefoneum,$telefonedois){
			if($this->isUserExist($username,$email)){
				return 0; 
			}else{
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `users` (`id`, `username`, `password`) VALUES (NULL, ?, ?);");
				$stmt->bind_param("ss",$username,$password);
				
				if($stmt->execute()){
				    
					$stmt = $this->con->prepare("SELECT `id` FROM `users` WHERE username = ? AND password = ?");
				    $stmt->bind_param("ss",$username,$password);
				    
				    if($stmt->execute()){
				        $id_users = $stmt->get_result()->fetch_assoc()['id'];
				        
				        $stmt = $this->con->prepare("INSERT INTO `profile`(`id`, `cpf`, `nome`, `sobrenome`, `users_id`) VALUES (null,?,?,?,?);");
				        $stmt->bind_param("ssss",$cpf,$nome,$sobrenome,$id_users);

				        if($stmt->execute()){
				            	$stmt = $this->con->prepare("SELECT `id` FROM `profile` WHERE users_id = ?");
				                $stmt->bind_param("s",$id_users);
				            
				           if($stmt->execute()){
				    
				                $id_profile = $stmt->get_result()->fetch_assoc()['id'];
				                
				                $stmt = $this->con->prepare("INSERT INTO `endereco`(`id`, `cidade`, `bairro`, `rua`, `cep`, `numero`, `profile_id`, `profile_users_id`) VALUES (null,?,?,?,?,?,?,?);");
				                $stmt->bind_param("sssssss",$cidade,$bairro,$rua,$cep,$numero,$id_profile,$id_users);
				                
    				            if($stmt->execute()){
    				                $stmt = $this->con->prepare("INSERT INTO `contato`(`id`, `email`, `telefoneUm`, `telefoneDois`, `profile_id`, `profile_users_id`) VALUES (null,?,?,?,?,?);");
				                    $stmt->bind_param("sssss",$email,$telefoneum,$telefonedois,$id_profile,$id_users);
				                    if($stmt->execute()){
				                        return 1;
				                    }
    				            }
				           }
				        }
				    }
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
        
        public function getPetsByUser($username){
			$stmt = $this->con->prepare("select a.nome as petname, a.peso, a.raca, a.ano_nascimento from users u, profile p, endereco e, contato c, animal a where u.id = p.users_id and p.id = e.profile_id and p.id = c.profile_id and p.id = a.profile_id and u.username = ?");
			$stmt->bind_param("s",$username);
			$stmt->execute();
			$result = $stmt->get_result();
			$pet = array();
			if($result->num_rows > 0){
			    
    			while($row = $result->fetch_assoc()) {
        			    $row_pet['petname'] = $row['petname'];
        			    $row_pet['peso'] = $row['peso'];
        			    $row_pet['raca'] = $row['raca'];
        			    $row_pet['ano_nascimento'] = $row['ano_nascimento'];
                        array_push($pet,$row_pet);
    			}
			    return $pet;
			}
			return null;
		}
        
        
		public function getUserByUsername($username){
			//$stmt = $this->con->prepare("SELECT * FROM users WHERE username = ?");
			$stmt = $this->con->prepare("select u.id ,u.username, u.password, p.cpf,p.nome,p.sobrenome, e.cidade, e.bairro, e.rua, e.cep, e.numero, c.email, c.telefoneUm, c.telefoneDois, a.nome as petname, a.peso, a.raca, a.ano_nascimento from users u, profile p, endereco e, contato c, animal a where u.id = p.users_id and p.id = e.profile_id and p.id = c.profile_id and p.id = a.profile_id and u.username = ?");
			$stmt->bind_param("s",$username);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}
		

		private function isUserExist($username, $email){
			$stmt = $this->con->prepare("SELECT u.id FROM users u JOIN contato c ON u.id = c.profile_id WHERE u.username = ? OR c.email = ?");
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