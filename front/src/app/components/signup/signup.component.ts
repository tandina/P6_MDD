import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "src/app/services/authentication/auth.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-signup",
  templateUrl: "./signup.component.html",
  styleUrls: ["./signup.component.css"],
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  errorMessage: string = "";
  error403: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.signupForm = this.formBuilder.group({
      email: ["", [Validators.required, Validators.email]],
      username: [
        "",
        [Validators.required, Validators.min(3), Validators.max(20)],
      ],
      password: [
        "",
        [Validators.required, Validators.min(3), Validators.max(20)],
      ],
    });
  }

  onSubmit(): void {
    if (this.signupForm.valid) {
      this.authService.signup(this.signupForm.value).subscribe(
        (data) => {
          this.signupForm.reset();
          this.errorMessage = "";
          this.router.navigate(["/login"]);
        },
        (error) => {
          console.log("error singup", error);
        }
      );
    } else {
      console.log("formulaire incorrect");
      this.error403 = true;
    }
  }
}
