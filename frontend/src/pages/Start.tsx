import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { motion, AnimatePresence } from "framer-motion";

const Start: React.FC = () => {
  const navigate = useNavigate();
  const [isExiting, setIsExiting] = useState(false);

  const handleStart = () => {
    setIsExiting(true); // 페이지 전환 트리거
    setTimeout(() => navigate("/login"), 1000); // 애니메이션 종료 후 이동
  };

  return (
    <div className="relative w-full h-screen overflow-hidden">
      <AnimatePresence>
        {!isExiting && (
          <motion.div
            className="absolute inset-0 flex flex-col items-center justify-center z-10 text-center"
            initial={{ y: 0 }}
            animate={{ y: 0 }}
            exit={{ y: "-100vh" }} // 위로 밀려 올라가는 애니메이션
            transition={{ duration: 1 }}
          >
            <motion.h1
              className="text-6xl font-bold mb-6 text-white"
              initial={{ opacity: 0, scale: 0.8 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ duration: 2.5, ease: "easeOut" }}
              style={{
                textShadow:
                  "0 0 5px rgba(255, 255, 255, 0.6), 0 0 5px #4D96FF, 0 0 5px #FF6B6B",
              }}
            >
              Emogram
            </motion.h1>

            <motion.p
              className="text-lg text-gray-200 mb-6"
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              transition={{ delay: 1, duration: 2.5 }}
            >
              당신의 감정을 기록하고, 특별한 기억을 저장하세요.
            </motion.p>

            <motion.button
              onClick={handleStart}
              className="bg-white/20 text-white py-2 px-6 rounded-lg backdrop-blur-md transition duration-200
                hover:bg-white/30 shadow-md hover:shadow-lg border border-white/10"
              initial={{ opacity: 0, scale: 0.8 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ delay: 1.5, duration: 2.5 }}
            >
              시작하기
            </motion.button>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
};

export default Start;
