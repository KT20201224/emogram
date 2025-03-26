import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import axios from "axios";

const Login: React.FC = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/api/users/login",
        {
          email,
          password,
        }
      );

      console.log("로그인 응답: ", response.data); // 응답 확인용 콘솔 출력

      const { token, userId } = response.data;

      // 토큰과 사용자 ID 저장
      if (token && userId) {
        localStorage.setItem("token", token);
        localStorage.setItem("userId", userId.toString());
        console.log("토큰과 사용자 ID가 성공적으로 저장되었습니다.");

        alert("로그인 성공!"); // 확인용 메시지
        navigate("/login-loading"); // 로그인 성공 후 로딩 페이지로 이동
      } else {
        setError("로그인 응답에 문제가 있습니다. 다시 시도해주세요.");
      }
    } catch (error: any) {
      console.error("로그인 요청 오류:", error);
      setError(error.response?.data?.message || "로그인 요청 실패.");
    }
  };

  return (
    <motion.div
      className="flex flex-col items-center justify-center min-h-screen"
      initial={{ y: "100vh" }}
      animate={{ y: 0 }}
      exit={{ y: "-100vh" }}
      transition={{ duration: 1 }}
    >
      <div className="bg-white p-8 rounded-lg shadow-lg w-96 backdrop-blur-md">
        <h2 className="text-2xl font-bold text-center mb-6">로그인</h2>
        {error && <p className="text-red-500 text-center mb-4">{error}</p>}
        <form onSubmit={handleLogin} className="space-y-4">
          <input
            type="email"
            placeholder="이메일"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded"
          />
          <input
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded"
          />
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
          >
            로그인
          </button>
        </form>
        <div className="text-center mt-4">
          <span>계정이 없으신가요? </span>
          <button
            onClick={() => navigate("/signup")}
            className="text-blue-600 hover:underline"
          >
            회원가입
          </button>
        </div>
      </div>
    </motion.div>
  );
};

export default Login;
