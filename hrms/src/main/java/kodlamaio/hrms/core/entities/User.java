package kodlamaio.hrms.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy=InheritanceType.JOINED) 
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "users"})
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="email")
	@Email
	@NotBlank(message="Email boş bırakılamaz")
	@NotNull(message="Email boş bırakılamaz")
	private String email;
	
	@Column(name="password")
	@NotBlank(message="Şifre boş bırakılamaz")
	@NotNull(message="Şifre boş bırakılamaz")
	private String password;
	
}
