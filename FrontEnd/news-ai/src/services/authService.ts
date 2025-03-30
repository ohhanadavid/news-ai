import config from "../config";

export const handleRefreshToken = async (
  refreshToken: string | null,
  setToken: (token: string | null) => void,
  logout: () => void
) => {
  if (!refreshToken) return logout();
  try {
    const res = await fetch(`${config.baseURL}/refreshToken`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ refreshToken }),
    });
    if (!res.ok) throw new Error("Refresh token failed");
    const data = await res.json();
    setToken(data.token);
    localStorage.setItem("token", data.token);
  } catch {
    logout();
  }
};
