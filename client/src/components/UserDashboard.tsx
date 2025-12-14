import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { sweetsAPI, inventoryAPI, Sweet } from '../services/api';
import './UserDashboard.css';

const UserDashboard: React.FC = () => {
  const { user, logout, isAdmin } = useAuth();
  const navigate = useNavigate();
  const [sweets, setSweets] = useState<Sweet[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('All');
  const [purchasing, setPurchasing] = useState<string | null>(null);

  const categories = ['All', 'Wedding', 'Diwali', 'Holi', 'Festival', 'Birthday', 'Premium Gift'];

  useEffect(() => {
    loadSweets();
  }, []);

  const loadSweets = async () => {
    try {
      const response = await sweetsAPI.getAll();
      setSweets(response.data);
    } catch (error) {
      console.error('Error loading sweets:', error);
    } finally {
      setLoading(false);
    }
  };

  const handlePurchase = async (sweet: Sweet) => {
    if (sweet.quantity === 0) {
      alert('This sweet is out of stock!');
      return;
    }

    setPurchasing(sweet.id);
    try {
      await inventoryAPI.purchase(sweet.id, 1);
      alert('Purchase successful!');
      loadSweets();
    } catch (error: any) {
      alert(error.response?.data?.message || 'Purchase failed');
    } finally {
      setPurchasing(null);
    }
  };

  const filteredSweets = sweets.filter((sweet) => {
    const matchesSearch = sweet.name.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory = selectedCategory === 'All' || sweet.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  if (loading) {
    return <div className="loading">Loading sweets...</div>;
  }

  return (
    <div className="user-dashboard">
      <header className="dashboard-header">
        <div className="header-left">
          <div className="logo">
            <span className="logo-icon">🍬</span>
            <div>
              <h1>Sugar Rush</h1>
              <p>Traditional Indian Sweets</p>
            </div>
          </div>
        </div>
        <div className="header-right">
          <span className="user-name">Welcome, {user?.username}!</span>
          {isAdmin() && (
            <button onClick={() => navigate('/admin')} className="btn-admin">
              Admin
            </button>
          )}
          <button onClick={logout} className="btn-logout">Logout</button>
        </div>
      </header>

      <div className="hero-section">
        <h2>Authentic Indian Sweets</h2>
        <p>Handcrafted with love, delivered fresh to your doorstep</p>
        <div className="search-bar">
          <span className="search-icon">🔍</span>
          <input
            type="text"
            placeholder="Search for sweets..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
      </div>

      <div className="filter-section">
        <span className="filter-label">Shop by Occasion:</span>
        <div className="filter-buttons">
          {categories.map((category) => (
            <button
              key={category}
              className={`filter-btn ${selectedCategory === category ? 'active' : ''}`}
              onClick={() => setSelectedCategory(category)}
            >
              {category}
            </button>
          ))}
        </div>
      </div>

      <div className="sweets-grid">
        {filteredSweets.map((sweet) => (
          <div key={sweet.id} className="sweet-card">
            <div className="sweet-header">
              <h3>{sweet.name}</h3>
              <span className="quantity-badge">{sweet.quantity > 0 ? `${sweet.quantity} left` : 'Out of Stock'}</span>
            </div>
            <div className="sweet-tag">{sweet.category}</div>
            <div className="sweet-footer">
              <span className="sweet-price">₹{sweet.price}</span>
              <button
                className={`btn-add ${sweet.quantity === 0 ? 'disabled' : ''}`}
                onClick={() => handlePurchase(sweet)}
                disabled={sweet.quantity === 0 || purchasing === sweet.id}
              >
                {purchasing === sweet.id ? 'Purchasing...' : sweet.quantity === 0 ? 'Out of Stock' : 'Purchase'}
              </button>
            </div>
          </div>
        ))}
      </div>

      {filteredSweets.length === 0 && (
        <div className="no-results">No sweets found matching your criteria.</div>
      )}
    </div>
  );
};

export default UserDashboard;

