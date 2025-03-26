import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import axios from "axios";

const LoginLoading: React.FC = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState<string | null>(null);

  useEffect(() => {
    const fetchUsername = async () => {
      try {
        const token = localStorage.getItem("token");

        if (!token) {
          navigate("/login"); // 토큰이 없으면 로그인 페이지로 이동
          return;
        }

        const response = await axios.get(
          "http://localhost:8080/api/users/profile",
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );

        setUsername(response.data.nickname);

        // 로딩이 끝난 후 Home 페이지로 이동
        setTimeout(() => navigate("/home"), 3000);
      } catch (error) {
        console.error("사용자 정보를 가져오는 데 실패했습니다.", error);
        navigate("/login");
      }
    };

    fetchUsername();
  }, [navigate]);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500">
      <motion.div
        className="text-white text-4xl font-bold mb-6"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 1.5, repeat: Infinity, repeatType: "reverse" }}
      >
        {username ? `${username}의 기억 저장소로 이동하는 중...` : "로딩 중..."}
      </motion.div>

      <motion.div
        className="w-16 h-16 border-4 border-t-transparent border-white rounded-full mt-6"
        animate={{ rotate: 360 }}
        transition={{ duration: 1, repeat: Infinity, ease: "linear" }}
      />
    </div>
  );
};

export default LoginLoading;
