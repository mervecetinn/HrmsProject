package kodlamaio.hrms.entities.concretes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import kodlamaio.hrms.core.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name="candidates")
public class Candidate extends User {
   
   @Column(name="first_name")
   private String firstName;
   
   @Column(name="last_name")
   private String lastName;
   
   @Column(name="identity_number")
   private String identityNumber;
   
   @Column(name="birth_date")
   private Date birthDate;
   

}
