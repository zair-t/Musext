import React, { useState } from "react";
import bg from "../img/loginRegistrationFon.svg";
import { Link } from "react-router-dom";
import UserService from "../services/UserService";

function Login() {
  const [data, setData] = useState();
  const [isLoading, setIsLoading] = useState(false);
  const [err, setErr] = useState('');

  const [user, setUser] = useState({
    email: "",
    password: ""
  });

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({...user, [e.target.name]: value});
  };

  const handleClick = async () => {
    setIsLoading(true);
    try {
      const response = await fetch('http://localhost:8000/api/login', {
        method: 'POST',
        body: JSON.stringify({
          email: user.email,
          password: user.password,
        }),
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error(`Error! status: ${response.status}`);
      }

      const result = await response.json();

      console.log('result is: ', JSON.stringify(result, null, 4));

      setData(result);
    } catch (err) {
      setErr(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  console.log(user);

  const validateUser = (e) => {
    e.preventDefault();
    UserService.validateUser(user).then((response) => {
      console.log(response);
    }).catch((error) => {
      console.log(e);
    });
  };

  return (
      <section
        className="vh-100 bg-image"
        style={{ backgroundImage: `url(${bg})` }}
      >
        <div className="container py-5 h-100">
          <div className="row d-flex justify-content-center align-items-center h-100">
            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
              <div
                className="card bg-dark text-white"
                style={{ borderRadius: "1rem" }}
              >
                <div className="card-body p-5 text-center">
                  <div className="mb-md-5 mt-md-4">
                    <h1 className="fw-bold mb-2 text-uppercase ">Login</h1>
                    <p className="text-white-50 mb-5">
                      Please enter your login and password!
                    </p>

                    <div className="form-outline form-white mb-4">
                      <input
                        type="email"
                        name="email"
                        value={user.email}
                        onChange={(e) => handleChange(e)}
                        id="typeEmailX"
                        className="form-control form-control-lg"
                      />
                      <label className="form-label" for="typeEmailX">
                        Email
                      </label>
                    </div>

                    <div className="form-outline form-white mb-4">
                      <input
                        type="password"
                        name="password"
                        value={user.password}
                        onChange={(e) => handleChange(e)}
                        id="typePasswordX"
                        className="form-control form-control-lg"
                      />
                      <label className="form-label" for="typePasswordX">
                        Password
                      </label>
                    </div>

                    <p className="small mb-5 pb-lg-2">
                      <a className="text-white-50" href="/reset">
                        Forgot password?
                      </a>
                    </p>

                    <button
                      onClick={handleClick}
                      className="btn btn-outline-light btn-lg px-5"
                      type="submit"
                    >
                      Login
                    </button>

                    <div className="d-flex justify-content-center text-center pt-4 mt-4 mb-0">
                      <a href="#!" className="text-white">
                        <i
                          className="fa fa-facebook"
                          style={{ fontSize: "30px" }}
                        ></i>
                      </a>
                      <a href="#!" className="text-white">
                        <i
                          className="fa fa-twitter px-5"
                          style={{ fontSize: "30px" }}
                        ></i>
                      </a>
                      <a href="#!" className="text-white">
                        <i
                          className="fa fa-google"
                          style={{ fontSize: "30px" }}
                        ></i>
                      </a>
                    </div>
                  </div>

                  <div>
                    <p className="pt-0 mt-4">
                      Don't have an account?{" "}
                      <Link to = "/registration" style={{color: 'white', fontWeight: 'bold'}}>Sign up</Link>
                    { /* <a href="/registration" className="text-white-50 fw-bold"> Sign Up </a>*/}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
  );
}

export default Login;
