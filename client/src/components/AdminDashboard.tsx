import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { sweetsAPI, inventoryAPI, Sweet } from '../services/api';
import './AdminDashboard.css';

const AdminDashboard: React.FC = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [sweets, setSweets] = useState<Sweet[]>([]);
  const [loading, setLoading] = useState(true);
  const [showAddForm, setShowAddForm] = useState(false);
  const [editingSweet, setEditingSweet] = useState<Sweet | null>(null);
  const [formData, setFormData] = useState({
    name: '',
    category: '',
    price: '',
    quantity: '',
  });
  const [restockData, setRestockData] = useState<{ [key: string]: string }>({});

  useEffect(() => {
    loadSweets();
  }, []);

  const loadSweets = async () => {
    try {
      const response = await sweetsAPI.getAll();
      setSweets(response.data);
    } catch (error) {
      console.error('Error loading sweets:', error);
      alert('Error loading sweets');
    } finally {
      setLoading(false);
    }
  };

  const handleAdd = () => {
    setFormData({ name: '', category: '', price: '', quantity: '' });
    setEditingSweet(null);
    setShowAddForm(true);
  };

  const handleEdit = (sweet: Sweet) => {
    setFormData({
      name: sweet.name,
      category: sweet.category,
      price: sweet.price.toString(),
      quantity: sweet.quantity.toString(),
    });
    setEditingSweet(sweet);
    setShowAddForm(true);
  };

  const handleDelete = async (id: string) => {
    if (!window.confirm('Are you sure you want to delete this sweet?')) {
      return;
    }
    try {
      await sweetsAPI.delete(id);
      alert('Sweet deleted successfully');
      loadSweets();
    } catch (error: any) {
      alert(error.response?.data?.message || 'Error deleting sweet');
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const sweetData = {
        name: formData.name,
        category: formData.category,
        price: parseFloat(formData.price),
        quantity: parseInt(formData.quantity),
      };

      if (editingSweet) {
        await sweetsAPI.update(editingSweet.id, sweetData);
        alert('Sweet updated successfully');
      } else {
        await sweetsAPI.create(sweetData);
        alert('Sweet added successfully');
      }
      setShowAddForm(false);
      setEditingSweet(null);
      loadSweets();
    } catch (error: any) {
      alert(error.response?.data?.message || 'Error saving sweet');
    }
  };

  const handleRestock = async (id: string) => {
    const quantity = parseInt(restockData[id] || '0');
    if (quantity <= 0) {
      alert('Please enter a valid quantity');
      return;
    }
    try {
      await inventoryAPI.restock(id, quantity);
      alert('Restocked successfully');
      setRestockData({ ...restockData, [id]: '' });
      loadSweets();
    } catch (error: any) {
      alert(error.response?.data?.message || 'Error restocking');
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="admin-dashboard">
      <header className="dashboard-header">
        <div className="header-left">
          <div className="logo">
            <span className="logo-icon">🍬</span>
            <div>
              <h1>Sugar Rush</h1>
              <p>Admin Dashboard</p>
            </div>
          </div>
        </div>
        <div className="header-right">
          <span className="user-name">Admin: {user?.username}</span>
          <button onClick={() => navigate('/dashboard')} className="btn-user-view">
            User View
          </button>
          <button onClick={logout} className="btn-logout">Logout</button>
        </div>
      </header>

      <div className="admin-content">
        <div className="admin-actions">
          <button onClick={handleAdd} className="btn-add-sweet">
            + Add New Sweet
          </button>
        </div>

        {showAddForm && (
          <div className="form-modal">
            <div className="form-card">
              <h3>{editingSweet ? 'Edit Sweet' : 'Add New Sweet'}</h3>
              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label>Name</label>
                  <input
                    type="text"
                    value={formData.name}
                    onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Category</label>
                  <select
                    value={formData.category}
                    onChange={(e) => setFormData({ ...formData, category: e.target.value })}
                    required
                  >
                    <option value="">Select Category</option>
                    <option value="Wedding">Wedding</option>
                    <option value="Diwali">Diwali</option>
                    <option value="Holi">Holi</option>
                    <option value="Festival">Festival</option>
                    <option value="Birthday">Birthday</option>
                    <option value="Premium Gift">Premium Gift</option>
                  </select>
                </div>
                <div className="form-group">
                  <label>Price (₹)</label>
                  <input
                    type="number"
                    step="0.01"
                    value={formData.price}
                    onChange={(e) => setFormData({ ...formData, price: e.target.value })}
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Quantity</label>
                  <input
                    type="number"
                    value={formData.quantity}
                    onChange={(e) => setFormData({ ...formData, quantity: e.target.value })}
                    required
                    min="0"
                  />
                </div>
                <div className="form-actions">
                  <button type="submit" className="btn-save">Save</button>
                  <button
                    type="button"
                    onClick={() => {
                      setShowAddForm(false);
                      setEditingSweet(null);
                    }}
                    className="btn-cancel"
                  >
                    Cancel
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}

        <div className="sweets-table-container">
          <table className="sweets-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Category</th>
                <th>Price (₹)</th>
                <th>Quantity</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {sweets.map((sweet) => (
                <tr key={sweet.id}>
                  <td>{sweet.name}</td>
                  <td>
                    <span className="category-badge">{sweet.category}</span>
                  </td>
                  <td>₹{sweet.price}</td>
                  <td>{sweet.quantity}</td>
                  <td>
                    <div className="action-buttons">
                      <button onClick={() => handleEdit(sweet)} className="btn-edit">
                        Edit
                      </button>
                      <div className="restock-group">
                        <input
                          type="number"
                          placeholder="Qty"
                          value={restockData[sweet.id] || ''}
                          onChange={(e) =>
                            setRestockData({ ...restockData, [sweet.id]: e.target.value })
                          }
                          className="restock-input"
                          min="1"
                        />
                        <button
                          onClick={() => handleRestock(sweet.id)}
                          className="btn-restock"
                        >
                          Restock
                        </button>
                      </div>
                      <button
                        onClick={() => handleDelete(sweet.id)}
                        className="btn-delete"
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {sweets.length === 0 && (
            <div className="no-sweets">No sweets available. Add your first sweet!</div>
          )}
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;

